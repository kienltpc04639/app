package com.kienltpc04639.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kienltpc04639.config.Config;

@Controller
public class AjaxController {
	@RequestMapping("/vnpay_ajax")
	public String handleAjaxRequest() throws UnsupportedEncodingException, UnknownHostException, InvalidKeyException,
			SignatureException, NoSuchAlgorithmException {
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		// Thiết lập định dạng ngày tháng theo chuẩn yyyyMMddHHmmss
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Đặt múi giờ cụ thể (Ví dụ: Asia/Ho_Chi_Minh)

		// Gán ngày hiện tại với định dạng chuẩn
		String createDate = sdf.format(currentDate);

		// Thiết lập ngày hết hạn (expireDate) xa hơn
		calendar.add(Calendar.DATE, 7); // Thêm 7 ngày
		Date futureDate = calendar.getTime();

		// Gán ngày hết hạn xa hơn với định dạng chuẩn
		String expireDate = sdf.format(futureDate);

		// Sử dụng createDate và expireDate trong chuỗi input data
		String input = "vnp_Amount=1000000" +
				"&vnp_Command=pay" +
				"&vnp_CreateDate=" + createDate +
				"&vnp_CurrCode=VND" +
				"&vnp_ExpireDate=" + expireDate +
				"&vnp_IpAddr=127.0.0.1" +
				"&vnp_Locale=vn" +
				"&vnp_OrderInfo=" + URLEncoder.encode("Thanh toan GD:8442", StandardCharsets.UTF_8) +
				"&vnp_OrderType=other" +
				"&vnp_ReturnUrl=" + URLEncoder.encode(Config.vnp_Returnurl, StandardCharsets.UTF_8) +
				"&vnp_TmnCode=ZRW18TX8" +
				"&vnp_TxnRef=" + Config.getRandomNumber(8) +
				"&vnp_Version=2.1.0";
		String vnp_SecureHash = Config.hmacSHA512("SNLNODLJHCHDAKWQUZEPFCIECKIRPTIE", input);
		input += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = Config.vnp_PayUrl + "?" + input;
		return "redirect:" + paymentUrl;
	}

}
