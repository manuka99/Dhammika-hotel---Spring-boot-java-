package com.hotel.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.EmailConfirmToken;
import com.hotel.management.model.User;
import com.hotel.management.repository.EmailConfirmTokenRepository;

@Service
public class EmailConfirmTokenserviceImpl implements EmailConfirmTokenService {

	private Logger logger = LoggerFactory.getLogger(EmailConfirmTokenserviceImpl.class);
	
	@Autowired
	private EmailConfirmTokenRepository emailConfirmTokenRepository;

	@Override
	public EmailConfirmToken getTokenByUser(User user) {
		return emailConfirmTokenRepository.findByUser(user);
	}

	@Override
	public boolean deleteToken(EmailConfirmToken emailConfirmToken) {

		boolean result = false;

		try {
			emailConfirmTokenRepository.delete(emailConfirmToken);
			result = true;
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@Override
	public boolean saveToken(EmailConfirmToken emailConfirmToken) {

		boolean result = false;

		try {
			emailConfirmTokenRepository.save(emailConfirmToken);
			result = true;
		} catch (Exception e) {
			result = false;
		}

		return result;
		
	}

}
