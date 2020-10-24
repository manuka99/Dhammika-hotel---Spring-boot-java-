package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.User;
import com.hotel.management.service.CategoryService;
import com.hotel.management.service.NotificationService;
import com.hotel.management.service.RoleService;

@Controller
public class PanelController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/panel/about")
	public String about() {

		return "panel/about";
	}

	@GetMapping("/panel/notifications")
	public String notifications(Model model) {
		model.addAttribute("notifications", notificationService.getAllNotificationsByUser(getPrincipalUser()));
		return "panel/notification";
	}

	@GetMapping("/panel/navigation")
	public String navigation(Model model) {

		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("roles", roleService.getAllRoles());

		return "fragments/panel/header";
	}

	@GetMapping("/panel")
	public String panelIndex() {

		return "/panel/index";
	}

	@GetMapping("/panel/product-analysis")
	public String panelFamousProduct() {

		return "/panel/chart/product";
	}

	@GetMapping("/panel/purchase-analysis")
	public String panelProductPurchases() {

		return "/panel/chart/purchase";
	}

	@GetMapping("/panel/orders-analysis")
	public String panelOrdersCharts() {

		return "/panel/chart/orders";
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
