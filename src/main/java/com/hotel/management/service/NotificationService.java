package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

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

	boolean deleteNotificationById(String id);

	boolean saveNotificationToDB(NotificationDB notification);

	/*
	 * for the user
	 */

	/*
	 * user place a order
	 */
	void NewUserOrder(OrderDB order);

	/*
	 * user update order
	 */
	void UpdateUserOrder(OrderDB order);

	/*
	 * user notified if admin update order
	 */
	//boolean UpdateUserOrderByAdmin(OrderDB order);

	/*
	 * user place a complain
	 */
	void NewUserComplain(Complain complain);

	/*
	 * user respond to the complain
	 */
	void NewUserResponse(Response_Complain response);

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
	void OrderUpdatedByAdminToAdmin(OrderDB order, User admin);

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
	void ResponsePlacedByAdminToAdmin(Response_Complain response);
	
	
	/*
	 * feedbacks
	 */
	
	void newFeedback(FeedBack feedback);
	
	void updateFeedback(FeedBack feedback);
	
	void deleteFeedback(FeedBack feedback);
	
	//boolean deleteFeedbackFromPanelToUser(FeedBack feedback);
	
	
	/*
	 * panelFeedbacks
	 */
	
	//boolean newFeedbackUserToPanel(FeedBack feedback);
	
	//boolean deleteFeedbackFromUserToPanel(FeedBack feedback);
	
	//boolean updateFeedbackFromUserToPanel(FeedBack feedback);
	
	void deleteFeedbackFromPanel(FeedBack feedback);
	
}
