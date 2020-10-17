package com.hotel.management.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hotel.management.model.Complain;
import com.hotel.management.model.FeedBack;
import com.hotel.management.model.NotificationDB;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Response_Complain;
import com.hotel.management.model.User;
import com.hotel.management.repository.NotificationRepository;
import com.hotel.management.util.CommonConstants;

@Service
public class NotificationServiceImpl implements NotificationService {

	private Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private RoleService roleService;

	@Override
	public List<NotificationDB> getAllNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteNotificationById(String id) {

		boolean result = false;

		try {
			notificationRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public List<NotificationDB> getAllNotificationsByUser(User user) {

		Sort sort = Sort.by("time").descending();
		return notificationRepository.findByUser(user, sort);
	}

	@Override
	public NotificationDB GetNotificationByUserAndNotID(User user, String notificationID) {

		return notificationRepository.findByUserAndNotificationID(user, notificationID);
	}

	@Override
	public boolean saveNotificationToDB(NotificationDB notification) {

		boolean result = false;

		try {

			notificationRepository.save(notification);
			result = true;

		} catch (Exception e) {
			logger.info("error saving notification to db");
			result = false;
		}

		return result;
	}

	/*
	 * for user
	 */

	@Async
	@Override
	public void NewUserOrder(OrderDB order) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(order.getUser());
		notification.setHeader("You have successfully placed the order( " + order.getOrderID() + " )");
		notification.setBody(order.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl(CommonConstants.ORDER_URI_MEMBER + order.getOrderID());

		saveNotificationToDB(notification);
		/*
		 * notify the panel
		 */
		NewOrderPanel(order);

	}

	@Async
	@Override
	public void UpdateUserOrder(OrderDB order) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(order.getUser());
		notification
				.setHeader("You have successfully " + order.getStatus() + " your order(" + order.getOrderID() + " )");
		notification.setBody(
				"The order you placed on " + order.getPlacedDate() + " has been " + order.getStatus() + " by you.");
		notification.setUrl(CommonConstants.ORDER_URI_MEMBER + order.getOrderID());

		saveNotificationToDB(notification);
		/*
		 * notify panel
		 */
		OrderUpdatedByUserToAdmin(order);

		// return saveNotificationToDB(notification);
	}

	@Async
	public void UpdateUserOrderByAdmin(OrderDB order) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(order.getUser());
		notification.setHeader("Your Order has been " + order.getStatus());
		notification
				.setBody(order.getUser().getUsername() + " you order has been updated. click to view more details..");
		notification.setUrl(CommonConstants.ORDER_URI_MEMBER + order.getOrderID());

		saveNotificationToDB(notification);
		// return saveNotificationToDB(notification);
	}

	@Async
	@Override
	public void NewUserComplain(Complain complain) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(complain.getUser());
		notification.setHeader("Your Complain has been placed successfully");
		notification.setBody("Complain reference ID: " + complain.getId() + " , click to view more details..");
		notification.setUrl(CommonConstants.COMPLAIN_URI_MEMBER + complain.getId());
		saveNotificationToDB(notification);
		/*
		 * notify panel
		 */
		UserComplainToPanel(complain);

		// return result;
	}

	@Async
	@Override
	public void NewUserResponse(Response_Complain response) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(response.getUser());
		notification.setHeader("Your Response has been placed successfully");
		notification.setBody(
				"Complain reference ID: " + response.getComplain().getId() + " , click to view more details..");
		notification.setUrl(CommonConstants.COMPLAIN_URI_MEMBER + response.getComplain().getId());
		saveNotificationToDB(notification);
		/*
		 * notify panel
		 */
		ResponsePlacedByUserToAdmin(response);

		// return result;
	}

	@Async
	public void NewResponseFromAdminToUser(Response_Complain response) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(response.getUser());
		notification.setHeader("Our team has responded to your complain");
		notification.setBody(
				"Complain reference ID: " + response.getComplain().getId() + " , click to view more details..");
		notification.setUrl(CommonConstants.COMPLAIN_URI_MEMBER + response.getComplain().getId());

		saveNotificationToDB(notification);
		// return saveNotificationToDB(notification);

	}

	@Async
	public void NewOrderPanel(OrderDB order) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("New Order: by " + order.getUser().getUsername());
				notification.setBody("There is a new order of order ID " + order.getOrderID());
				notification.setUrl(CommonConstants.ORDER_URI_ADMIN + order.getOrderID());
				saveNotificationToDB(notification);

			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		// return result;
	}

	@Async
	public void OrderUpdatedByUserToAdmin(OrderDB order) {

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {
				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Order(" + order.getOrderID() + "): " + order.getStatus() + " (By User "
						+ order.getUser().getUsername() + " )");
				notification.setBody("User who placed the order has decided to " + order.getStatus() + " the order.");
				notification.setUrl(CommonConstants.ORDER_URI_ADMIN + order.getOrderID());
				saveNotificationToDB(notification);
			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

	}

	@Async
	@Override
	public void OrderUpdatedByAdminToAdmin(OrderDB order, User admin) {

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {
				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Order(" + order.getOrderID() + "): " + order.getStatus() + " (By Administrator "
						+ admin.getUsername() + " )");
				notification.setBody(
						"This order was updated by the system administrator with ID " + admin.getUserID() + " .");
				notification.setUrl(CommonConstants.ORDER_URI_ADMIN + order.getOrderID());
				saveNotificationToDB(notification);
			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		/*
		 * notify user
		 */
		UpdateUserOrderByAdmin(order);

		// return result;
	}

	@Async
	public void UserComplainToPanel(Complain complain) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_COMPLAIN_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {
				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("New Complain: by " + complain.getUser().getUsername());
				notification.setBody("There is a new complain of complain ID " + complain.getId());
				notification.setUrl(CommonConstants.COMPLAIN_URI_ADMIN + complain.getId());
				saveNotificationToDB(notification);
			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		// return result;
	}

	@Async
	public void ResponsePlacedByUserToAdmin(Response_Complain response) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_COMPLAIN_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {
				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Complain(" + response.getComplain().getId() + "): " + " (By User "
						+ response.getComplain().getUser().getUsername() + " )");
				notification.setBody("User who placed the complain has responded, view more.....");
				notification.setUrl(CommonConstants.COMPLAIN_URI_ADMIN + response.getComplain().getId());
				saveNotificationToDB(notification);
			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		// return result;
	}

	@Async
	@Override
	public void ResponsePlacedByAdminToAdmin(Response_Complain response) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_COMPLAIN_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Complain(" + response.getComplain().getId() + ") - (By Administrator "
						+ response.getUser().getUsername() + " )");
				notification.setBody("This complain was responded by the system administrator with ID "
						+ response.getUser().getUserID() + " .");
				notification.setUrl(CommonConstants.COMPLAIN_URI_ADMIN  + response.getComplain().getId());
				saveNotificationToDB(notification);

			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		/*
		 * notify user
		 */
		NewResponseFromAdminToUser(response);

		// return result;
	}

	@Async
	@Override
	public void newFeedback(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was placed successfully");
		notification.setBody(feedback.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl(CommonConstants.FEEDBACK_URI_MEMBER  + feedback.getProduct().getProductID());
		saveNotificationToDB(notification);
		/*
		 * notify the panel
		 */
		newFeedbackUserToPanel(feedback);

	}

	@Async
	@Override
	public void updateFeedback(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was updated successfully");
		notification.setBody(feedback.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl(CommonConstants.FEEDBACK_URI_MEMBER + feedback.getProduct().getProductID());
		saveNotificationToDB(notification);
		/*
		 * notify the panel
		 */
		updateFeedbackFromUserToPanel(feedback);

	}

	@Async
	@Override
	public void deleteFeedback(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was deleted successfully");
		notification.setBody(feedback.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl(CommonConstants.FEEDBACK_URI_MEMBER + feedback.getProduct().getProductID());
		saveNotificationToDB(notification);
		/*
		 * notify the panel
		 */
		deleteFeedbackFromUserToPanel(feedback);

	}

	@Async
	@Override
	public void deleteFeedbackFromPanel(FeedBack feedback) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_FOOD_MANAGER);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader(
						"Feedback of product " + feedback.getProduct().getName() + "was deleted by administrator");
				notification.setBody(feedback.getUser().getUsername() + " : feedback :" + feedback.getMessage());
				notification.setUrl(CommonConstants.FEEDBACK_URI_ADMIN + feedback.getProduct().getProductID());
				saveNotificationToDB(notification);

			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		/*
		 * notify the user
		 */
		deleteFeedbackFromPanelToUser(feedback);

	}

	@Async
	public void deleteFeedbackFromPanelToUser(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was deleted by the administration");
		notification.setBody(
				feedback.getUser().getUsername() + ", we had to remove your feedback as it was violating our rules.");
		notification.setUrl(CommonConstants.FEEDBACK_URI_ADMIN + feedback.getProduct().getProductID());
		saveNotificationToDB(notification);
	}

	@Async
	public void newFeedbackUserToPanel(FeedBack feedback) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_FOOD_MANAGER);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);
		
		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {
				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Feedback(" + feedback.getFeedbackID() + "): " + " (By User "
						+ feedback.getUser().getUsername() + " )");
				notification.setBody(
						"New feedback was added to the " + feedback.getProduct().getName() + ", view more.....");
				notification.setUrl(CommonConstants.FEEDBACK_URI_ADMIN + feedback.getProduct().getProductID());
				saveNotificationToDB(notification);
			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		// return result;

	}

	@Async
	public void updateFeedbackFromUserToPanel(FeedBack feedback) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_FOOD_MANAGER);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {
				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Feedback(" + feedback.getFeedbackID() + "): " + " (By User "
						+ feedback.getUser().getUsername() + " )");
				notification.setBody(
						"Feedback was updated on the product " + feedback.getProduct().getName() + ", view more.....");
				notification.setUrl(CommonConstants.FEEDBACK_URI_ADMIN + feedback.getProduct().getProductID());
				saveNotificationToDB(notification);
			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

		// return result;

	}

	@Async
	public void deleteFeedbackFromUserToPanel(FeedBack feedback) {

		List<String> role_names = new ArrayList<>();
		role_names.add(CommonConstants.ROLE_ADMIN);
		role_names.add(CommonConstants.ROLE_FOOD_MANAGER);
		role_names.add(CommonConstants.ROLE_ORDER_MANAGER);

		for (User user : roleService.getUsersByRoleName(role_names)) {

			try {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Feedback(" + feedback.getFeedbackID() + "): " + " ( placed By User "
						+ feedback.getUser().getUsername() + " )");
				notification.setBody("This Feedback on the product " + feedback.getProduct().getName()
						+ " was deleted by the customer, view more.....");
				notification.setUrl(CommonConstants.FEEDBACK_URI_ADMIN + feedback.getProduct().getProductID());
				saveNotificationToDB(notification);

			} catch (Exception e) {
				logger.info("error saving noificatuion");
			}

		}

	}

}
