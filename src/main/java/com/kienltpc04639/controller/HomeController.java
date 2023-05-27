package com.kienltpc04639.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}
	@RequestMapping("/pay")
	public String pay(Model model) {
		return "vnpay_pay";
	}
	@RequestMapping("/refund")
	public String refund(Model model) {
		return "vnpay_refund";
	}
	@RequestMapping("/querydr")
	public String querydr(Model model) {
		return "vnpay_querydr";
	}
	@RequestMapping("/return")
	public String returnPage(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
	    model.addAttribute("param", allRequestParams);
		return "vnpay_return";
	}
}
