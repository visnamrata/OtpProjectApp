package com.rapipay.OtpSender.services;

//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.ZoneOffset;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rapipay.OtpSender.dao.Repository;
import com.rapipay.OtpSender.entity.UserDetails;

@Service
public class EmailService implements ServiceInterface {

	@Autowired
	private Repository repo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	 private static final long OTP_VALID_DURATION = 1*60 * 1000;
	 Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	 @Override
		public int generateOtp() {
			// TODO Auto-generated method stub
			int otp=(int)(Math.random()*900000)+100000;
			return otp;
		}

		@Override
		public boolean sendOtp(String to) {
			// TODO Auto-generated method stub
			int otp=generateOtp();
			//UserDetails user=addOtp(to, otp, new Date(), attempts);
			String finalOTP=String.valueOf(otp);
			
			UserDetails newUser=new UserDetails();
			newUser.setOtp(otp);
			newUser.setEmailId(to);
			SimpleMailMessage message=new SimpleMailMessage();
			message.setTo(to);
			message.setText(finalOTP);
			mailSender.send(message);
			newUser.setGeneratedDate(new Date());
		   repo.save(newUser);
			System.out.println("Mail sent sucesfully");		
			System.out.println(otp);
			Date sentdate=message.getSentDate();
			if(sentdate==newUser.getGeneratedDate())
			{
				return true;
			}
			return false;
		}


	@Override
	public String validateOtp(String otp, String emailID) {
		// TODO Auto-generated method stub
		UserDetails userotp=repo.findById(emailID).orElse(null);
		long currentTimeInMillis = System.currentTimeMillis();
		logger.info("Validating OTP .... ");
		     if(userotp==null)
		     {
		    	 logger.info("Invalid OTP .... ");
				return "invalid";
		    }
		    long otpRequestedTimeInMillis = userotp.getGeneratedDate().getTime();
		     if(otp.length()<6 || otp.length()>6)
		    {
		    logger.info("Invalid OTP .... ");
			return "invalid";
		    }
		
			
			
			if(userotp.getOtp()==Integer.parseInt(otp)&& (otpRequestedTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis))
			{
				logger.info("Valid OTP .... ");
				return "valid";
			}
			
			else if(otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis)
			{
				repo.delete(userotp);
				logger.info("OTP Expired , please resend.... ");
				return "expired";
			}
			else
			{
				logger.info("invalid OTP .... ");
				return "invalid";
			}
				
	
}

	
}

	

	