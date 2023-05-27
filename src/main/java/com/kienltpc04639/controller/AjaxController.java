package com.kienltpc04639.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kienltpc04639.config.Config;

@Controller
public class AjaxController {
	@RequestMapping("/vnpay_ajax")
	public String handleAjaxRequest() throws UnsupportedEncodingException, UnknownHostException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		// Lấy ngày hiện tại
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String createDate = now.format(formatter);

		// Tính ngày hết hạn cách 15 phút
		LocalDateTime expireDateTime = now.plusMinutes(15);
		String expireDate = expireDateTime.format(formatter);

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
		String vnp_SecureHash = Config.hmacSHA512("SNLNODLJHCHDAKWQUZEPFCIECKIRPTIE",input);
        input += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + input;
        System.out.println(paymentUrl);
		return "redirect:" + paymentUrl;
	}


}
