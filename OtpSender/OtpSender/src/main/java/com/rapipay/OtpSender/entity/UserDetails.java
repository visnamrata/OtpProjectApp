package com.rapipay.OtpSender.entity;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userDetails")
public class UserDetails {

	@Id
	private String emailId;
	private int otp;
//	private LocalTime generatedTime;
//	private LocalDate generatedDate;
	private Date generatedDate;

	public UserDetails( String emailId, int otp, Date generatedDate) {
		super();
		this.emailId = emailId;
		this.otp = otp;
		this.generatedDate = generatedDate;
	}

	public UserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "UserDetails [emailId=" + emailId + ", otp=" + otp + "]";
	}

}
