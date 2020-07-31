package com.hotel.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotel.management.model.User;
import com.hotel.management.service.UserService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/add")
	public String addUserByAdmin(User user) {
		String pwd = user.getPassword();
		user.setPassword(passwordEncoder.encode(pwd));
		userService.saveUser(user);
		return "user added";

	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/all")
	public String securedHello() {
		return "Secured Hello";
	}

}
