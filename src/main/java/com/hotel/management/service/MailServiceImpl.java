package com.hotel.management.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hotel.management.config.ApplicationUrl;
import com.hotel.management.controller.OrderController;
import com.hotel.management.model.Complain;
import com.hotel.management.model.Mail;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.User;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailServiceImpl implements MailService {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private ApplicationUrl applicationUrl;

	@Async
	public boolean sendEmail(Mail mail) {

		boolean result = false;

		try {

			MimeMessage message = sender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message);

			// Using a subfolder such as /templates here
			freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

			Template t = freemarkerConfig.getTemplate("email-template.ftl");
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

			helper.setTo(mail.getMailTo());
			helper.setText(text, true);
			helper.setSubject(mail.getMailSubject());

			sender.send(message);

			result = true;

		} catch (Exception e) {

			result = false;
			logger.info("error sendind mail");
		}

		return result;

	}

	@Override
	@Async
	public void userRegistrationEmail(User user) throws Exception {

		try {

			Mail mail = new Mail();
			// mail.setMailFrom("mykeylogger49@gmail.com");
			// mail.setMailTo("manukayasas94@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Registration");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Thanks for registering on our site.");
			object.put("para2", "Before we get started, we'll need to verify your email.");
			object.put("mailURl", applicationUrl.getUrl() + "/confirm-email?uid=" + user.getUserID() + "&token="
					+ user.getEmailConfirmToken().getConfirmationToken());
			object.put("mailURlName", "Confirm my email");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public boolean resendAccountActivationEmail(User user) throws Exception {

		boolean result = false;

		try {

			Mail mail = new Mail();
			// mail.setMailTo("manukayasas94@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Verify Email");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Please verify your email");
			object.put("para2", "Amazing deals, updates, interesting news right in your inbox.");
			object.put("mailURl", applicationUrl.getUrl() + "/confirm-email?uid=" + user.getUserID() + "&token="
					+ user.getEmailConfirmToken().getConfirmationToken());
			object.put("mailURlName", "Confirm my email");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			result = false;
			logger.info("error sendind mail");
		}

		return result;

	}

	@Async
	@Override
	public void orderPlacedEmail(OrderDB order) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(order.getUser().getEmail());
			mail.setMailSubject("Dhammika Hotel: Order Reciept");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", order.getUser().getUsername());
			object.put("header", "");
			object.put("para1", "Your order have been placed successfully");
			object.put("para2", "Thank you for using our services.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "View my order");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void orderStatusEmail(OrderDB order) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(order.getUser().getEmail());
			mail.setMailSubject("Dhammika Hotel: Order is " + order.getStatus());

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", order.getUser().getUsername());
			object.put("header", "");
			object.put("para1", "Your order(" + order.getOrderID() + ") is " + order.getStatus() + ".");
			object.put("para2", "Thank you for using our services.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "View my order");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void profileUpdateEmail(User user, String oldEmail) throws Exception {

		try {

			Mail mail = new Mail();
			// mail.setMailTo("manukayasas94@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Profile Update");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Your user profile was updated successfully.");
			object.put("para2", "If this was not you, please let us know by repling to this email.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "My profile");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

		if (oldEmail != null) {

			try {

				Mail mail2 = new Mail();
				// mail.setMailTo("manukayasas94@gmail.com");
				mail2.setMailTo(oldEmail);
				mail2.setMailSubject("Dhammika Hotel: Email Changed");

				Map<String, Object> object2 = new HashMap<String, Object>();
				object2.put("username", user.getUsername());
				object2.put("header", "");
				object2.put("para1",
						"Your account was updated successfully and your new email is " + user.getEmail() + ".");
				object2.put("para2", "If this was not you, please let us know by repling to this email.");
				object2.put("mailURl", applicationUrl.getUrl());
				object2.put("mailURlName", "My profile");
				mail2.setModel(object2);

				sendEmail(mail2);

			} catch (Exception e) {
				logger.info("error sendind mail");
			}

		}

	}

	@Async
	@Override
	public void passwordChangedEmail(User user) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo("manukayasas94@gmail.com");
			// mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Password Changed");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Your password was changed successfully.");
			object.put("para2", "If this was not you, please let us know by repling to this email.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "My profile");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void passwordRecoveryEmail(User user, String password) throws Exception {

		try {

			Mail mail = new Mail();
			// mail.setMailTo("manukayasas94@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Password Recovery");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Your Account password was recovered successfully");
			object.put("para2", "<br>This is your login credentials: <br><br>Email: " + user.getEmail()
					+ "<br>New Password: " + password + "<br><br>Please change your password after login.");
			object.put("mailURl", applicationUrl.getUrl() + "/user/security");
			object.put("mailURlName", "Change Password");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void userPlaceComplain(Complain complain) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(complain.getUser().getEmail());
			mail.setMailSubject("Dhammika Hotel: Complain Placed");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", complain.getUser().getUsername());
			object.put("header", "");
			object.put("para1", "Your Complain was placed successfully");
			object.put("para2",
					"Our main intention is to provide a quality, friendly service to all our users. Our team will be always there for you and we will assist you as soon as possible.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "View user complain");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void userComplainPlaceResponse(Complain complain) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(complain.getUser().getEmail());
			mail.setMailSubject("Dhammika Hotel: Response Placed");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", complain.getUser().getUsername());
			object.put("header", "");
			object.put("para1", "Your Response on  complain(" + complain.getId() + ") was placed successfully");
			object.put("para2",
					"Our main intention is to provide a quality, friendly service to all our users. Our team will be always there for you and we will assist you as soon as possible.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "View user Complain");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void userComplainRecieveResponse(Complain complain) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(complain.getUser().getEmail());
			mail.setMailSubject("Dhammika Hotel: Complain Responded");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", complain.getUser().getUsername());
			object.put("header", "");
			object.put("para1", "Our team responsed to your complain(" + complain.getId() + ") which was placed on "
					+ complain.getTime());
			object.put("para2",
					"Our main intention is to provide a quality, friendly service to all our users. Our team will be always there for you and we will assist you as soon as possible.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "View user Complain");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void panelUserPasswordUpdate(User user) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Password Updated");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Your password was updated by our team for security purpose.");
			object.put("para2",
					"Our main intention is to provide a quality, friendly service to all our users. Our team will be always there for you and we will assist you as soon as possible.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "Login");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void panelUserProfileUpdate(User user) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Profile Updated");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Your profile was updated by our team for security purpose.");
			object.put("para2",
					"Our main intention is to provide a quality, friendly service to all our users. Our team will be always there for you and we will assist you as soon as possible.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "My Account");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void panelUserAccountLocked(User user) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Account Locked");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1",
					"Your Account was locked by our team for security purpose. If you have any inquries please email us so that we will sort it out.");
			object.put("para2",
					"Our main intention is to provide a quality, friendly service to all our users. Our team will be always there for you and we will assist you as soon as possible.");
			object.put("mailURl", applicationUrl.getUrl());
			object.put("mailURlName", "My Account");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

	@Async
	@Override
	public void panelUserPasswordUpdateWithCredentials(User user, String password) throws Exception {

		try {

			Mail mail = new Mail();
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("Dhammika Hotel: Password Updated");

			Map<String, Object> object = new HashMap<String, Object>();
			object.put("username", user.getUsername());
			object.put("header", "");
			object.put("para1", "Your password was updated by our team for security purpose.");
			object.put("para2", "<br>This is your login credentials: <br><br>Email: " + user.getEmail()
					+ "<br>New Password: " + password + "<br><br>Please change your password after login.");
			object.put("mailURl", applicationUrl.getUrl() + "/user/security");
			object.put("mailURlName", "Change Password");
			mail.setModel(object);

			sendEmail(mail);

		} catch (Exception e) {
			logger.info("error sendind mail");
		}

	}

}
