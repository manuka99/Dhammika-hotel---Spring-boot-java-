package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import com.hotel.management.model.EmailConfirmToken;
import com.hotel.management.model.User;

public interface EmailConfirmTokenService {

	EmailConfirmToken getTokenByUser(User user);
	boolean deleteToken(EmailConfirmToken emailConfirmToken);
	boolean saveToken(EmailConfirmToken emailConfirmToken);
	
}
