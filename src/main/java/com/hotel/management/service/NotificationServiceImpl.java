package com.hotel.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.management.model.Complain;
import com.hotel.management.model.FeedBack;
import com.hotel.management.model.NotificationDB;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Response_Complain;
import com.hotel.management.model.User;
import com.hotel.management.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private RoleService roleService;

	@Override
	public List<NotificationDB> getAllNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * public void saveNotification(NotificationDB notification_User) throws
	 * java.lang.NullPointerException {
	 * 
	 * DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	 * LocalDateTime now = LocalDateTime.now();
	 * 
	 * if (notification_User.getType().equals("Order")) {
	 * 
	 * 
	 * if (notification_User.getMemberSender() != null) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now));
	 * notification.setUser(notification_User.getMemberSender());
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderMember());
	 * notification.setBodyPanel(notification_User.getBodyMember());
	 * 
	 * notification.setUrl("GetMyOrders?orderID=" +
	 * notification_User.getCustomID1());
	 * 
	 * notificationRepository.save(notification);
	 * 
	 * }
	 * 
	 * Set<User> users = new HashSet<>();
	 * 
	 * try { users.addAll(roleService.getRoleByName("ADMIN").getUsers()); } catch
	 * (Exception e) { } try {
	 * users.addAll(roleService.getRoleByName("FOOD_MANAGER").getUsers()); } catch
	 * (Exception e) { }
	 * 
	 * try { users.addAll(roleService.getRoleByName("ORDER_MANAGER").getUsers()); }
	 * catch (Exception e) { }
	 * 
	 * for (User user : users) {
	 * 
	 * if (notification_User.getMemberSender() != user) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * 
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now)); notification.setUser(user);
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderPanel());
	 * notification.setBodyPanel(notification_User.getBodyPanel());
	 * 
	 * notification.setUrl("RetrieveOrdersPanel?oID=" +
	 * notification_User.getCustomID1() + "&userID=" +
	 * notification_User.getCustomID2());
	 * 
	 * notificationRepository.save(notification); } }
	 * 
	 * }
	 * 
	 * else if (notification_User.getType().equals("ContactUs")) {
	 * 
	 * Set<User> users = new HashSet<>();
	 * 
	 * try { users.addAll(roleService.getRoleByName("ADMIN").getUsers()); } catch
	 * (Exception e) { }
	 * 
	 * try {
	 * users.addAll(roleService.getRoleByName("CUSTOMER_SERVICE_MANAGER").getUsers()
	 * ); } catch (Exception e) { }
	 * 
	 * for (User user : users) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now)); notification.setUser(user);
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderPanel());
	 * notification.setBodyPanel(notification_User.getBodyPanel());
	 * 
	 * notification.setUrl("RespondContactUsPanel?cuid=" +
	 * notification_User.getCustomID1()); notificationRepository.save(notification);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * else if (notification_User.getType().equals("Complain")) {
	 * 
	 * if (notification_User.getMemberSender() != null) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now));
	 * notification.setUser(notification_User.getMemberSender());
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderMember());
	 * notification.setBodyPanel(notification_User.getBodyMember());
	 * 
	 * notification.setUrl("RetrieveInquiryUser?inquiryID=" +
	 * notification_User.getCustomID1()); notificationRepository.save(notification);
	 * 
	 * }
	 * 
	 * Set<User> users = new HashSet<>();
	 * 
	 * try { users.addAll(roleService.getRoleByName("ADMIN").getUsers()); } catch
	 * (Exception e) { // TODO: handle exception }
	 * 
	 * try {
	 * users.addAll(roleService.getRoleByName("CUSTOMER_SERVICE_MANAGER").getUsers()
	 * ); } catch (Exception e) { // TODO: handle exception }
	 * 
	 * for (User user : users) {
	 * 
	 * if (notification_User.getMemberSender() != user) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now)); notification.setUser(user);
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderPanel());
	 * notification.setBodyPanel(notification_User.getBodyPanel());
	 * 
	 * notification.setUrl("RetrieveInquiryPanel?inquiryID=" +
	 * notification_User.getCustomID1());
	 * 
	 * notificationRepository.save(notification); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * else if (notification_User.getType().equals("feedback")) {
	 * 
	 * if (notification_User.getMemberSender() != null) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now));
	 * notification.setUser(notification_User.getMemberSender());
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderMember());
	 * notification.setBodyPanel(notification_User.getBodyMember());
	 * 
	 * notification.setUrl("product?fID=" + notification_User.getCustomID1());
	 * notificationRepository.save(notification); }
	 * 
	 * Set<User> users = new HashSet<>();
	 * 
	 * try { users.addAll(roleService.getRoleByName("ADMIN").getUsers()); } catch
	 * (Exception e) { // TODO: handle exception } try {
	 * users.addAll(roleService.getRoleByName("FOOD_MANAGER").getUsers()); } catch
	 * (Exception e) { // TODO: handle exception }
	 * 
	 * for (User user : users) {
	 * 
	 * if (notification_User.getMemberSender() != user) {
	 * 
	 * NotificationDB notification = new NotificationDB();
	 * notification.setNotificationID(UniqueIdGenerator.userIDGenerator("noti"));
	 * notification.setTime(dtf.format(now)); notification.setUser(user);
	 * 
	 * notification.setHeaderPanel(notification_User.getHeaderPanel());
	 * notification.setBodyPanel(notification_User.getBodyPanel());
	 * 
	 * notification.setUrl("product?fID=" + notification_User.getCustomID1());
	 * 
	 * notificationRepository.save(notification); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

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
	public boolean updateNotification(NotificationDB notification) {

		boolean result = false;

		try {
			notificationRepository.save(notification);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	/*
	 * for user
	 */

	@Override
	public boolean NewUserOrder(OrderDB order) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(order.getUser());
		notification.setHeader("You have successfully placed the order( " + order.getOrderID() + " )");
		notification.setBody(order.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl("GetMyOrders?orderID=" + order.getOrderID());

		/*
		 * notify the panel
		 */
		NewOrderPanel(order);

		return saveNotificationToDB(notification);

	}

	@Override
	public boolean UpdateUserOrder(OrderDB order) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(order.getUser());
		notification
				.setHeader("You have successfully " + order.getStatus() + " your order(" + order.getOrderID() + " )");
		notification.setBody(
				"The order you placed on " + order.getPlacedDate() + " has been " + order.getStatus() + " by you.");
		notification.setUrl("GetMyOrders?orderID=" + order.getOrderID());

		/*
		 * notify panel
		 */
		OrderUpdatedByUserToAdmin(order);

		return saveNotificationToDB(notification);
	}

	public boolean UpdateUserOrderByAdmin(OrderDB order) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(order.getUser());
		notification.setHeader("Your Order has been " + order.getStatus());
		notification
				.setBody(order.getUser().getUsername() + " you order has been updated. click to view more details..");
		notification.setUrl("GetMyOrders?orderID=" + order.getOrderID());

		return saveNotificationToDB(notification);
	}

	@Override
	public boolean NewUserComplain(Complain complain) {

		boolean result = false;

		NotificationDB notification = new NotificationDB();
		notification.setUser(complain.getUser());
		notification.setHeader("Your Complain has been placed successfully");
		notification.setBody("Complain reference ID: " + complain.getId() + " , click to view more details..");
		notification.setUrl("RetrieveInquiryUser?inquiryID=" + complain.getId());
		result = saveNotificationToDB(notification);
		/*
		 * notify panel
		 */
		UserComplainToPanel(complain);

		return result;
	}

	@Override
	public boolean NewUserResponse(Response_Complain response) {

		boolean result = false;
		NotificationDB notification = new NotificationDB();
		notification.setUser(response.getUser());
		notification.setHeader("Your Response has been placed successfully");
		notification.setBody(
				"Complain reference ID: " + response.getComplain().getId() + " , click to view more details..");
		notification.setUrl("RetrieveInquiryUser?inquiryID=" + response.getComplain().getId());
		result = saveNotificationToDB(notification);
		/*
		 * notify panel
		 */
		ResponsePlacedByUserToAdmin(response);

		return result;
	}

	public boolean NewResponseFromAdminToUser(Response_Complain response) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(response.getUser());
		notification.setHeader("Our team has responded to your complain");
		notification.setBody(
				"Complain reference ID: " + response.getComplain().getId() + " , click to view more details..");
		notification.setUrl("RetrieveInquiryUser?inquiryID=" + response.getComplain().getId());

		return saveNotificationToDB(notification);

	}

	public boolean NewOrderPanel(OrderDB order) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");
		role_names.add("ORDER_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("New Order: by " + order.getUser().getUsername());
				notification.setBody("There is a new order of order ID " + order.getOrderID());
				notification.setUrl("GetMyOrders?orderID=" + order.getOrderID());
				result = saveNotificationToDB(notification);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	public boolean OrderUpdatedByUserToAdmin(OrderDB order) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");
		role_names.add("ORDER_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Order(" + order.getOrderID() + "): " + order.getStatus() + " (By User "
						+ order.getUser().getUsername() + " )");
				notification.setBody("User who placed the order has decided to " + order.getStatus() + " the order.");
				notification.setUrl("GetMyOrders?orderID=" + order.getOrderID());
				result = saveNotificationToDB(notification);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@Override
	public boolean OrderUpdatedByAdminToAdmin(OrderDB order, User admin) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");
		role_names.add("ORDER_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Order(" + order.getOrderID() + "): " + order.getStatus() + " (By Administrator "
						+ admin.getUsername() + " )");
				notification.setBody(
						"This order was updated by the system administrator with ID " + admin.getUserID() + " .");
				notification.setUrl("GetMyOrders?orderID=" + order.getOrderID());
				result = saveNotificationToDB(notification);

			}

		} catch (Exception e) {
			result = false;
		}

		/*
		 * notify user
		 */
		UpdateUserOrderByAdmin(order);

		return result;
	}

	public boolean UserComplainToPanel(Complain complain) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("New Complain: by " + complain.getUser().getUsername());
				notification.setBody("There is a new complain of complain ID " + complain.getId());
				notification.setUrl("GetMyOrders?orderID=" + complain.getId());
				result = saveNotificationToDB(notification);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	public boolean ResponsePlacedByUserToAdmin(Response_Complain response) {
		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Complain(" + response.getComplain().getId() + "): " + " (By User "
						+ response.getComplain().getUser().getUsername() + " )");
				notification.setBody("User who placed the complain has responded, view more.....");
				notification.setUrl("GetMyOrders?orderID=" + response.getComplain().getId());
				result = saveNotificationToDB(notification);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@Override
	public boolean ResponsePlacedByAdminToAdmin(Response_Complain response) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Complain(" + response.getComplain().getId() + ") - (By Administrator "
						+ response.getUser().getUsername() + " )");
				notification.setBody("This complain was responded by the system administrator with ID "
						+ response.getUser().getUserID() + " .");
				notification.setUrl("GetMyOrders?orderID=" + response.getComplain().getId());
				result = saveNotificationToDB(notification);

			}

		} catch (Exception e) {
			result = false;
		}

		/*
		 * notify user
		 */
		NewResponseFromAdminToUser(response);

		return result;
	}

	@Override
	public boolean saveNotificationToDB(NotificationDB notification) {

		boolean result = false;

		try {

			notificationRepository.save(notification);
			result = true;

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@Override
	public boolean newFeedback(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was placed successfully");
		notification.setBody(feedback.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl("product?productID=" + feedback.getProduct().getProductID());

		/*
		 * notify the panel
		 */
		newFeedbackUserToPanel(feedback);

		return saveNotificationToDB(notification);

	}

	@Override
	public boolean updateFeedback(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was updated successfully");
		notification.setBody(feedback.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl("product?productID=" + feedback.getProduct().getProductID());

		/*
		 * notify the panel
		 */
		updateFeedbackFromUserToPanel(feedback);

		return saveNotificationToDB(notification);
	}

	@Override
	public boolean deleteFeedback(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was deleted successfully");
		notification.setBody(feedback.getUser().getUsername() + ", thank you very much for using our services.");
		notification.setUrl("product?productID=" + feedback.getProduct().getProductID());

		/*
		 * notify the panel
		 */
		deleteFeedbackFromUserToPanel(feedback);

		return saveNotificationToDB(notification);

	}

	@Override
	public boolean deleteFeedbackFromPanel(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification
				.setHeader("Feedback of product " + feedback.getProduct().getName() + "was deleted by administrator");
		notification.setBody(feedback.getUser().getUsername() + " : feedback :" + feedback.getMessage());
		notification.setUrl("product?productID=" + feedback.getProduct().getProductID());

		/*
		 * notify the user
		 */
		deleteFeedbackFromPanelToUser(feedback);

		return saveNotificationToDB(notification);
	}

	public boolean deleteFeedbackFromPanelToUser(FeedBack feedback) {

		NotificationDB notification = new NotificationDB();
		notification.setUser(feedback.getUser());
		notification.setHeader("Your feedback was deleted by the administration");
		notification.setBody(
				feedback.getUser().getUsername() + ", we had to remove your feedback as it was violating our rules.");
		notification.setUrl("product?productID=" + feedback.getProduct().getProductID());

		return saveNotificationToDB(notification);
	}

	public boolean newFeedbackUserToPanel(FeedBack feedback) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Feedback(" + feedback.getFeedbackID() + "): " + " (By User "
						+ feedback.getUser().getUsername() + " )");
				notification.setBody(
						"New feedback was added to the " + feedback.getProduct().getName() + ", view more.....");
				notification.setUrl("product?productID=" + feedback.getProduct().getProductID());
				result = saveNotificationToDB(notification);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;

	}

	public boolean updateFeedbackFromUserToPanel(FeedBack feedback) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Feedback(" + feedback.getFeedbackID() + "): " + " (By User "
						+ feedback.getUser().getUsername() + " )");
				notification.setBody(
						"Feedback was updated on the product " + feedback.getProduct().getName() + ", view more.....");
				notification.setUrl("product?productID=" + feedback.getProduct().getProductID());
				result = saveNotificationToDB(notification);
			}

		} catch (Exception e) {
			result = false;
		}

		return result;

	}

	public boolean deleteFeedbackFromUserToPanel(FeedBack feedback) {

		boolean result = false;

		List<String> role_names = new ArrayList<>();
		role_names.add("ADMIN");
		role_names.add("FOOD_MANAGER");

		try {

			for (User user : roleService.getUsersByRoleName(role_names)) {

				NotificationDB notification = new NotificationDB();
				notification.setUser(user);
				notification.setHeader("Feedback(" + feedback.getFeedbackID() + "): " + " ( placed By User "
						+ feedback.getUser().getUsername() + " )");
				notification.setBody("This Feedback on the product " + feedback.getProduct().getName()
						+ " was deleted by the administration, view more.....");
				notification.setUrl("product?productID=" + feedback.getProduct().getProductID());
				result = saveNotificationToDB(notification);

				/*
				 * notify user
				 */
				deleteFeedbackFromPanelToUser(feedback);

			}

		} catch (Exception e) {
			result = false;
		}

		return result;

	}

}
