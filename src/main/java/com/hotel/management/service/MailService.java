package com.hotel.management.service;

import com.hotel.management.model.Complain;
import com.hotel.management.model.Mail;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.User;

public interface MailService {

	boolean sendEmail(Mail mail) throws Exception;

	boolean userRegistrationEmail(User user) throws Exception;
	
	boolean resendAccountActivationEmail(User user) throws Exception;

	boolean orderPlacedEmail(OrderDB order) throws Exception;

	boolean orderStatusEmail(OrderDB order) throws Exception;

	boolean profileUpdateEmail(User user, String oldEmail) throws Exception;
	
	boolean passwordChangedEmail(User user) throws Exception;
	
	boolean passwordRecoveryEmail(User user, String password) throws Exception;
	
	boolean userPlaceComplain(Complain complain) throws Exception;
	
	boolean userComplainPlaceResponse(Complain complain) throws Exception;
	
	boolean userComplainRecieveResponse(Complain complain) throws Exception;
	
	
	/*
	 * to send panel profile update email
	 */
	
	boolean panelUserPasswordUpdate(User user) throws Exception;
	
	boolean panelUserPasswordUpdateWithCredentials(User user, String password) throws Exception;
	
	boolean panelUserProfileUpdate(User user) throws Exception;
	
	boolean panelUserAccountLocked(User user) throws Exception;

}
