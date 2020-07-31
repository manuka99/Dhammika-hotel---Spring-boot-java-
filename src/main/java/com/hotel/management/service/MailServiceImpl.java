package com.hotel.management.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hotel.management.config.ApplicationUrl;
import com.hotel.management.model.Complain;
import com.hotel.management.model.Mail;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.User;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private ApplicationUrl applicationUrl;

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
			// TODO: handle exception
		}

		return result;

	}

	@Override
	public boolean userRegistrationEmail(User user) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;

	}

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

		}

		return result;

	}

	@Override
	public boolean orderPlacedEmail(OrderDB order) throws Exception {

		boolean result = false;

		try {

			Mail mail = new Mail();
			// mail.setMailTo("manukayasas94@gmail.com");
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

			result = false;

		}

		return result;

	}

	@Override
	public boolean orderStatusEmail(OrderDB order) throws Exception {

		boolean result = false;

		try {

			Mail mail = new Mail();
			// mail.setMailTo("manukayasas94@gmail.com");
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

			result = false;

		}

		return result;

	}

	@Override
	public boolean profileUpdateEmail(User user, String oldEmail) throws Exception {

		boolean result = false;

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

			result = false;

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

				result = false;

			}

		}

		return result;

	}

	@Override
	public boolean passwordChangedEmail(User user) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;

	}

	@Override
	public boolean passwordRecoveryEmail(User user, String password) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;

	}

	@Override
	public boolean userPlaceComplain(Complain complain) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;

	}

	@Override
	public boolean userComplainPlaceResponse(Complain complain) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;

	}

	@Override
	public boolean userComplainRecieveResponse(Complain complain) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;

	}

	@Override
	public boolean panelUserPasswordUpdate(User user) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;
	}

	@Override
	public boolean panelUserProfileUpdate(User user) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;
	}

	@Override
	public boolean panelUserAccountLocked(User user) throws Exception {

		boolean result = false;

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

			result = false;

		}

		return result;
	}

	@Override
	public boolean panelUserPasswordUpdateWithCredentials(User user, String password) throws Exception {
		
		boolean result = false;

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

			result = false;

		}

		return result;
	}

}
