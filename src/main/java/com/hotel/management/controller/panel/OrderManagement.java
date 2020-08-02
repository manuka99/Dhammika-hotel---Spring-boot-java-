package com.hotel.management.controller.panel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.User;
import com.hotel.management.service.MailService;
import com.hotel.management.service.NotificationService;
import com.hotel.management.service.OrderService;

@Controller
public class OrderManagement {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/panel/orders")
	public String getAllOrders(Model model) {

		model.addAttribute("orders", orderService.getAllOrders(false));

		return "panel/order/orders";
	}

	@PostMapping("/panel/orders/order/deleteOrder")
	public String deleteOrder(HttpServletRequest request, Model model) {

		String[] oids = {};

		if (request.getParameter("oids[]") != null) {
			oids = request.getParameterValues("oids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		for (String oid : oids) {

			if (orderService.deleteOrderById(oid))
				deletedItems.add(oid);

			else
				notDeleted.add(oid);

		}

		if (deletedItems.size() != oids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedItems.size());
		model.addAttribute("countSend", oids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedItems", deletedItems);
		model.addAttribute("displayMessage", true);

		return "panel/order/orders";

	}

	@GetMapping("/panel/orders/order")
	public String getAllOrderById(@RequestParam("orderID") String orderID, Model model) {

		try {
			model.addAttribute("order", orderService.getOrderByIdAndTemp(orderID, false));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "panel/order/order";
	}

	@PostMapping("/panel/orders/order/orderUpdated")
	public @ResponseBody boolean updateOrder(@RequestParam("orderID") String orderID,
			@RequestParam("status") String status, @RequestParam("dDate") String dDate,
			@RequestParam("payment") String payment) {
		
		boolean result = false;
		
		OrderDB order = orderService.getOrderByIdAndTemp(orderID, false);
		order.setDeliveredDate(dDate);
		order.getPayment().setPaymentStatus(payment);
		order.setStatus(status);
		
		result = orderService.saveOrder(order);
		
		if(result) {
			
			try {
				/*
				 * logged in user
				 */
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = new User();

				if (principal instanceof CurrentUser) {
					user = ((CurrentUser) principal).getUser();
				}
				
				mailService.orderStatusEmail(order);
				notificationService.OrderUpdatedByAdminToAdmin(order, user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return result;

	}

}
