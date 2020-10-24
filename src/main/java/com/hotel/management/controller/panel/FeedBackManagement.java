package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hotel.management.model.FeedBack;
import com.hotel.management.service.FeedBackService;
import com.hotel.management.service.NotificationService;

@Controller
public class FeedBackManagement {

	@Autowired
	private FeedBackService feedbackService;

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/panel/feedbacks")
	public String getAllFeedbacks(Model model) {

		model.addAttribute("feedbacks", feedbackService.getAllFedbacksSortTime());

		return "panel/feedback/feedbacks";
	}

	@PostMapping("/panel/feedbacks/deleteFeedback")
	public String deleteFeedbacks(HttpServletRequest request, Model model) {

		String[] fids = {};

		if (request.getParameter("fids[]") != null) {
			fids = request.getParameterValues("fids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		for (String fid : fids) {

			try {

				FeedBack feedback = feedbackService.getFeedBackById(fid);

				if (feedbackService.deleteFeedBackById(fid)) {
					deletedItems.add(fid);
					notificationService.deleteFeedbackFromPanel(feedback);
				}

				else
					notDeleted.add(fid);
			} catch (Exception e) {
				notDeleted.add(fid);
			}

		}

		if (deletedItems.size() != fids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedItems.size());
		model.addAttribute("countSend", fids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedItems", deletedItems);
		model.addAttribute("displayMessage", true);

		return "panel/feedback/feedbacks";

	}

}
