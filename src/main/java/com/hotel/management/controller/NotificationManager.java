package com.hotel.management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.NotificationDB;
import com.hotel.management.model.User;
import com.hotel.management.service.NotificationService;

@Controller
public class NotificationManager {

	@Autowired
	private NotificationService notificationService;

	private static final Logger logger = LoggerFactory.getLogger(NotificationManager.class);

	@GetMapping("/user/notifications")
	public String GetMemberNotifications(Model model) {

		model.addAttribute("notifications", notificationService.getAllNotificationsByUser(getPrincipalUser()));
		return "member/notification";

	}

	@GetMapping("user/readNotification")
	@ResponseBody
	public boolean memeberReadNotification(@RequestParam("notificationID") String notID) {

		boolean result = false;

		try {
			NotificationDB notification = notificationService.GetNotificationByUserAndNotID(getPrincipalUser(), notID);

			if (notification != null) {

				notification.setSeen(true);
				result = notificationService.saveNotificationToDB(notification);

			}
		} catch (Exception e) {
			logger.info(e.toString() + "message: " + e.getMessage());
		}

		return result;

	}

	@GetMapping("user/deleteNotification")
	@ResponseBody
	public boolean memeberDeleteNotification(@RequestParam("notificationID") String notID) {

		boolean result = false;

		try {
			NotificationDB notification = notificationService.GetNotificationByUserAndNotID(getPrincipalUser(), notID);

			if (notification != null)
				result = notificationService.deleteNotificationById(notID);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@GetMapping("/user/notificationsCount")
	@ResponseBody
	public int GetMemberNotificationsUnreadCount(Model model) {

		int count = 0;

		try {
			for (NotificationDB notification : notificationService.getAllNotificationsByUser(getPrincipalUser())) {

				if (notification.isSeen() == false)
					++count;

			}
		} catch (Exception e) {
			logger.info(e.toString() + "message: " + e.getMessage());
		}

		return count;

	}

	public User getPrincipalUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
		}

		return user;
	}
}
