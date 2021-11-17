package com.rapipay.OtpSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.rapipay.OtpSender.controller.EmailController;
import com.rapipay.OtpSender.dao.Repository;
import com.rapipay.OtpSender.entity.UserDetails;
import com.rapipay.OtpSender.services.EmailService;

@SpringBootTest
class OtpSenderApplicationTests {

	@Autowired
	private Repository repo;

	@Autowired
	private EmailService service;

	@Autowired
	private EmailController controller;
	@Autowired
	private JavaMailSender mailSender;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkFormat() {
		// expected output should be number
		String expectedOtp = "^\\s*(?:\\S\\s*){0,9}$";
		int actualOtp = service.generateOtp();
		System.out.println(actualOtp);
		assertEquals(true, String.valueOf(actualOtp).matches(expectedOtp));

	}

	@Test
	public void checkEmailSend() {
		// email sent or not properly
		UserDetails user = new UserDetails();
		user.setEmailId("vishnoinamrata@gmail.com");
		boolean isSent = service.sendOtp(user.getEmailId());
		assertThat(isSent);
	}

	@Test
	public void checkLengthOfOtp() {
		// Otp should be of length 6
		int actualOtp = service.generateOtp();
		int expectedLength = 6;
		int actaulLength = String.valueOf(actualOtp).length();
		assertEquals(expectedLength, actaulLength);
	}

	@Test
	public void checkTimer() {
		// After 60 seconds otp expires
		long expectedTimer = 1 * 60 * 1000;
		UserDetails user = new UserDetails();
		user.setEmailId("vishnoinamrata@gmail.com");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailId());
		message.setText(String.valueOf(service.generateOtp()));
		mailSender.send(message);
		user.setGeneratedDate(new Date());
		long currentTimeInMillis = System.currentTimeMillis();
		long otpRequestedTimeInMillis = user.getGeneratedDate().getTime();
		assertThat(otpRequestedTimeInMillis + expectedTimer > currentTimeInMillis);
	}

	@Test
	public void checkSave() {
		String email = "vishnoinamrata@gmail.com";
		int otp = service.generateOtp();
		Date time = new Date();
		UserDetails user = new UserDetails(email, otp, time);
		repo.save(user);
		UserDetails exists = repo.getById(email);
		assertThat(exists != null);

	}

	@Test
	public void checkExpiration() {

		long expectedTimer = 1 * 60 * 1000;
		UserDetails user = new UserDetails();
		user.setEmailId("vishnoinamrata@gmail.com");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailId());
		message.setText(String.valueOf(service.generateOtp()));
		mailSender.send(message);
		user.setGeneratedDate(new Date());
		long currentTimeInMillis = System.currentTimeMillis();
		long otpRequestedTimeInMillis = user.getGeneratedDate().getTime();
		assertFalse(otpRequestedTimeInMillis + expectedTimer < currentTimeInMillis);
	}

	@Test
	public void checkValidateReturn() {
		String actual = controller.validate("234564", "vishnoinamrata@gmail.com");
		assertFalse(actual.isEmpty());
	}

}
