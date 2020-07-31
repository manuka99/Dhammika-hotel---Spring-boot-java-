package com.hotel.management.service;

import java.util.List;

import com.hotel.management.model.Complain;
import com.hotel.management.model.FeedBack;
import com.hotel.management.model.NotificationDB;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Response_Complain;
import com.hotel.management.model.User;

public interface NotificationService {

	List<NotificationDB> getAllNotifications();

	List<NotificationDB> getAllNotificationsByUser(User user);

	NotificationDB GetNotificationByUserAndNotID(User user, String notificationID);

	//void saveNotification(NotificationDB notification);

	boolean updateNotification(NotificationDB notification);

	boolean deleteNotificationById(String id);

	boolean saveNotificationToDB(NotificationDB notification);

	/*
	 * for the user
	 */

	/*
	 * user place a order
	 */
	boolean NewUserOrder(OrderDB order);

	/*
	 * user update order
	 */
	boolean UpdateUserOrder(OrderDB order);

	/*
	 * user notified if admin update order
	 */
	//boolean UpdateUserOrderByAdmin(OrderDB order);

	/*
	 * user place a complain
	 */
	boolean NewUserComplain(Complain complain);

	/*
	 * user respond to the complain
	 */
	boolean NewUserResponse(Response_Complain response);

	/*
	 * user recieves a response from the admin
	 */
	//boolean NewResponseFromAdminToUser(Response_Complain response);

	/*
	 * for the panel
	 */

	/*
	 * new order to the admin
	 */
	//boolean NewOrderPanel(OrderDB order);

	/*
	 * notify if user update his Order
	 */
	//boolean OrderUpdatedByUserToAdmin(OrderDB order);

	/*
	 * notify if admin update the order
	 */
	boolean OrderUpdatedByAdminToAdmin(OrderDB order, User admin);

	/*
	 * new complain panel
	 */
	//boolean UserComplainToPanel(Complain complain);

	/*
	 * response by user who placed the complain to admin
	 */
	//boolean ResponsePlacedByUserToAdmin(Response_Complain response);

	/*
	 * new response from admin to admin
	 */
	boolean ResponsePlacedByAdminToAdmin(Response_Complain response);
	
	
	/*
	 * feedbacks
	 */
	
	boolean newFeedback(FeedBack feedback);
	
	boolean updateFeedback(FeedBack feedback);
	
	boolean deleteFeedback(FeedBack feedback);
	
	//boolean deleteFeedbackFromPanelToUser(FeedBack feedback);
	
	
	/*
	 * panelFeedbacks
	 */
	
	//boolean newFeedbackUserToPanel(FeedBack feedback);
	
	//boolean deleteFeedbackFromUserToPanel(FeedBack feedback);
	
	//boolean updateFeedbackFromUserToPanel(FeedBack feedback);
	
	boolean deleteFeedbackFromPanel(FeedBack feedback);
	
}
