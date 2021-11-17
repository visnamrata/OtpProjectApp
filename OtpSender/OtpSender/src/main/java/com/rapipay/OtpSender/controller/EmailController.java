package com.rapipay.OtpSender.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rapipay.OtpSender.services.EmailService;




@Controller
public class EmailController {
	
	@Autowired
	private EmailService service;

	
	@PostMapping(value="/getOtp")
	public ModelAndView getOtp(@RequestParam("email") String email)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("email",email);
		//modelAndView.addObject("attempt",email);
		
		modelAndView.setViewName("validate");
		boolean re=service.sendOtp(email);
		return modelAndView;
		
	}
	
	
	@RequestMapping(value="/home")
	public String home()
	{
		return "home";
		
	}
	
	@PostMapping("/validateotp")
	public String validate(@RequestParam("otp") String otp,@RequestParam("email") String email)
	{
		 
		return service.validateOtp(otp,email);
		
	}


}
