package com.rapipay.OtpSender.services;

import java.util.Date;

import org.springframework.http.ResponseEntity;

import com.rapipay.OtpSender.entity.UserDetails;


public interface ServiceInterface {

	
	public int generateOtp();
	public boolean sendOtp(String key);

	public String validateOtp(String otp, String emailID);
	
}
