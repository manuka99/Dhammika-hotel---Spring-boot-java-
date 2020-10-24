package com.hotel.management.controller;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.FeedBack;
import com.hotel.management.model.Product;
import com.hotel.management.model.Role;
import com.hotel.management.model.User;
import com.hotel.management.service.CurrencyGeneratorService;
import com.hotel.management.service.FeedBackService;
import com.hotel.management.service.NotificationService;
import com.hotel.management.service.OrderService;
import com.hotel.management.service.ProductService;
import com.hotel.management.util.UniqueIdGenerator;

@Controller
public class FeedBackController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private FeedBackService feedBackService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CurrencyGeneratorService currencyGeneratorService;

	private Logger logger = LoggerFactory.getLogger(FeedBackController.class);

	@PostMapping("/user/addFeedBack")
	@ResponseBody
	public boolean postFeedBack(@RequestParam(value = "productID", required = false) String productID,
			@RequestParam(value = "feedbackText", required = true) String feedbackText,
			@RequestParam(value = "rating", required = false) String rating, Model model) {

		boolean result = false;
		boolean addFeedback = true;

		FeedBack feedback = new FeedBack();
		feedback.setFeedbackID(UniqueIdGenerator.userIDGenerator("fed"));
		feedback.setMessage(feedbackText);

		try {

			Integer.parseInt(rating);

		} catch (Exception e) {
			addFeedback = false;
		}

		try {
			Product product = productService.getProductById(productID);
			if (orderService.hasProductOfUserOrder(product, getPrincipalUser()) && addFeedback) {

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();

				feedback.setTime(dtf.format(now));
				feedback.setRating(rating);
				feedback.setProduct(product);
				feedback.setUser(getPrincipalUser());
				result = feedBackService.saveFeedback(feedback);

				if (result)
					notificationService.newFeedback(feedback);

			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

	@GetMapping("/user/editFeedBack")
	public String editFeedBack(@RequestParam(value = "productID", required = false) String productID,
			@RequestParam(value = "feedbackID", required = false) String feedbackID, Model model) {

		try {

			Product product = productService.getProductById(productID);

			if (product != null && getPrincipalUser() != null) {

				FeedBack feedback = feedBackService.getFeedBackByIdAndUserAndProduct(feedbackID, getPrincipalUser(),
						product);

				model.addAttribute("editFeedback", feedback);

				model.addAttribute("product", product);
				model.addAttribute("userReview", getPrincipalUser());
				model.addAttribute("canReview", orderService.hasProductOfUserOrder(product, getPrincipalUser()));
				model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "products/product";

	}

	@PostMapping("/user/updateFeedBack")
	@ResponseBody
	public boolean updateFeedBack(@RequestParam(value = "productID", required = false) String productID,
			@RequestParam(value = "feedbackID", required = false) String feedbackID,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "rating", required = false) String rating, Model model) {

		boolean result = false;

		try {

			Product product = productService.getProductById(productID);

			if (product != null && getPrincipalUser() != null) {

				FeedBack feedback = feedBackService.getFeedBackByIdAndUserAndProduct(feedbackID, getPrincipalUser(),
						product);

				if (feedback != null) {

					boolean updateFeedback = true;

					if (message != null)
						feedback.setMessage(message);

					try {
						if (rating != null) {
							Integer.parseInt(rating);
							feedback.setRating(rating);
						}
					} catch (Exception e) {
						updateFeedback = false;

					}

					if (updateFeedback)
						result = feedBackService.saveFeedback(feedback);

					if (result)
						notificationService.updateFeedback(feedback);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exception caught");
		}

		return result;

	}

	@GetMapping("/user/deleteFeedBack")
	@ResponseBody
	public boolean deleteFeedBack(@RequestParam(value = "productID", required = false) String productID,
			@RequestParam(value = "feedbackID", required = false) String feedbackID, Model model) {

		boolean result = false;

		try {

			Product product = productService.getProductById(productID);

			if (product != null && getPrincipalUser() != null) {

				FeedBack feedback = feedBackService.getFeedBackByIdAndUserAndProduct(feedbackID, getPrincipalUser(),
						product);

				if (feedback != null) {
					result = feedBackService.deleteFeedBackById(feedbackID);
					notificationService.deleteFeedback(feedback);
				} else {

					FeedBack feedback2 = feedBackService.getFeedBackById(feedbackID);

					for (Role role : getPrincipalUser().getRoles()) {

						if (role.getName().equals("ADMIN")) {
							result = feedBackService.deleteFeedBackById(feedbackID);
							notificationService.deleteFeedbackFromPanel(feedback2);
							break;
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exception");
		}

		return result;

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
