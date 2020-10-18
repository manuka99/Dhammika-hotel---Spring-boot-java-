package com.hotel.management.controller.panel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.User;
import com.hotel.management.service.MailService;
import com.hotel.management.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@GetMapping("/panel/user/profile")
	public String PanelUserProfile(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User userSave = new User();

		if (principal instanceof CurrentUser) {
			userSave = ((CurrentUser) principal).getUser();
		}

		model.addAttribute("userSave", userSave);
		return "panel/profile/profile";
	}

	@PostMapping("/panel/user/profileUpdate")
	public String PanelUserProfileUpdate(@ModelAttribute("userSave") User saveUser, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/*
		 * logged in user
		 */
		User user = new User();

		CurrentUser currentUser = null;

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
			currentUser = ((CurrentUser) principal);

		}

		/*
		 * in db
		 */
		User userEmail = userService.findByEmail(user.getEmail());

		if (userEmail == null || userEmail.getUserID().equals(user.getUserID())) {

			User existingUser = userService.getUserById(user.getUserID());
			existingUser.setFname(saveUser.getFname());
			existingUser.setLname(saveUser.getLname());
			existingUser.setAddress(saveUser.getAddress());
			existingUser.setEmail(saveUser.getEmail());
			existingUser.setPhone(saveUser.getPhone());
			existingUser.setDateOfBirth(saveUser.getDateOfBirth());

			if (user.getEmail().equals(existingUser.getEmail()) == false)
				existingUser.setEnabled(false);

			boolean result = userService.updateUser(existingUser);

			if (result && user.getEmail().equals(existingUser.getEmail())) {
				model.addAttribute("message", "updated");
				currentUser.setUser(existingUser);
				Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser,
						currentUser.getPassword(), currentUser.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				mailService.profileUpdateEmail(existingUser, null);
			}

			else if (result) {
				model.addAttribute("message", "emailChanged");
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (auth != null) {
					new SecurityContextLogoutHandler().logout(request, response, auth);
				}
				mailService.profileUpdateEmail(existingUser, user.getEmail());
			}

			else
				model.addAttribute("message", "error");

		} else
			model.addAttribute("message", "error");

		return "panel/profile/profile";

	}

	@PostMapping("/panel/user/passwordUpdate")
	@ResponseBody
	public boolean PanelUserPasswordwordUpdate(@RequestParam("oldPass") String oldPassword,
			@RequestParam("newPass") String newPassword, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		boolean result = false;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
		}

		User existingUser = userService.getUserById(user.getUserID());

		System.out.println(existingUser.getPassword() + "sdsd " + oldPassword);

		if (passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
			System.out.println("sdsdsdsd");
			existingUser.setPassword(passwordEncoder.encode(newPassword));
			result = userService.updateUser(existingUser);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}

			mailService.passwordChangedEmail(existingUser);
		}

		return result;
	}

}
