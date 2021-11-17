package com.rapipay.OtpSender.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.rapipay.OtpSender.entity.UserDetails;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<UserDetails, String>{

	//Optional<UserDetails> findById(String emailId);
   
}
