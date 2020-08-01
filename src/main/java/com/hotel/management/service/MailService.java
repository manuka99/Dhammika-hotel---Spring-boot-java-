package com.hotel.management.service;

import com.hotel.management.model.Complain;
import com.hotel.management.model.Mail;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.User;

public interface MailService {

	boolean sendEmail(Mail mail) throws Exception;

	boolean resendAccountActivationEmail(User user) throws Exception;

	void userRegistrationEmail(User user) throws Exception;

	/*
	 * user profile update
	 */
	void profileUpdateEmail(User user, String oldEmail) throws Exception;

	void passwordChangedEmail(User user) throws Exception;

	void passwordRecoveryEmail(User user, String password) throws Exception;
	
	
	/*
	 * orders
	 */

	void orderPlacedEmail(OrderDB order) throws Exception;

	void orderStatusEmail(OrderDB order) throws Exception;
	
	
	/*
	 * complains
	 */

	void userPlaceComplain(Complain complain) throws Exception;

	void userComplainPlaceResponse(Complain complain) throws Exception;

	void userComplainRecieveResponse(Complain complain) throws Exception;

	/*
	 * to send panel profile update emails to users
	 */

	void panelUserPasswordUpdate(User user) throws Exception;

	void panelUserPasswordUpdateWithCredentials(User user, String password) throws Exception;

	void panelUserProfileUpdate(User user) throws Exception;

	void panelUserAccountLocked(User user) throws Exception;

}
