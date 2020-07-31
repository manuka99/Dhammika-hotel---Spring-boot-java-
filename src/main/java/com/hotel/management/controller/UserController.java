package com.hotel.management.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotel.management.model.Cart;
import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.EmailConfirmToken;
import com.hotel.management.model.Role;
import com.hotel.management.model.User;
import com.hotel.management.service.EmailConfirmTokenService;
import com.hotel.management.service.MailService;
import com.hotel.management.service.RoleService;
import com.hotel.management.service.UserService;
import com.hotel.management.util.UniqueIdGenerator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MailService mailService;

	@Autowired
	private EmailConfirmTokenService emailConfirmTokenService;

	@GetMapping("/register/emailCheck")
	public @ResponseBody boolean checkEmailExist(@RequestParam("email") String email, Model model) {

		boolean result = false;

		try {

			User user = userService.findByEmail(email);

			if (user != null)
				result = true;

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@GetMapping("/register")
	public String showRegister(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "memberArea/register";
	}

	@PostMapping("/saveUser")
	public String RegisterUser(@ModelAttribute("user") User user, Model model) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		/*
		 * set user role as member
		 */
		Set<Role> roles = new HashSet<>();
		Role role = roleService.getRoleByName("MEMBER");
		role.getUsers().add(user);
		roles.add(role);
		user.setRoles(roles);

		/*
		 * set cart
		 */

		Cart cart = new Cart();
		cart.setUser(user);
		user.setCart(cart);

		EmailConfirmToken emailConfirmToken = new EmailConfirmToken(user);
		user.setEmailConfirmToken(emailConfirmToken);
		userService.saveUser(user);

		/*
		 * send registration email
		 */
		mailService.userRegistrationEmail(user);

		/*
		 * send token mail
		 */

		return "redirect: /login";
	}

	@GetMapping("/user/profile")
	public String UserProfile(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User userSave = new User();

		if (principal instanceof CurrentUser) {
			userSave = ((CurrentUser) principal).getUser();
		}

		model.addAttribute("userSave", userSave);
		return "member/profile";
	}

	@GetMapping("/user/profileUpdate")
	public String UserProfileUpdate(@ModelAttribute("userSave") User saveUser, HttpServletRequest request,
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

		User existingUser = userService.getUserById(user.getUserID());
		existingUser.setFname(saveUser.getFname());
		existingUser.setLname(saveUser.getLname());
		existingUser.setAddress(saveUser.getAddress());
		existingUser.setEmail(saveUser.getEmail());
		existingUser.setPhone(saveUser.getPhone());
		existingUser.setDateOfBirth(saveUser.getDateOfBirth());

		if (user.getEmail().equals(existingUser.getEmail()) == false)
			existingUser.setEnabled(false);

		userService.updateUser(existingUser);

		if (user.getEmail().equals(existingUser.getEmail())) {
			currentUser.setUser(existingUser);
			Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser,
					currentUser.getPassword(), currentUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			mailService.profileUpdateEmail(existingUser, null);
		}

		else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			mailService.profileUpdateEmail(existingUser, user.getEmail());
		}

		return UserProfile(model);
	}

	@GetMapping("/user/security")
	public String UserSecurity(Model model) {
		return "member/security";
	}

	@PostMapping("/user/passwordUpdate")
	@ResponseBody
	public boolean UserPasswordwordUpdate(@ModelAttribute("userSave") User saveUser,
			@RequestParam("oldPass") String oldPassword, @RequestParam("newPass") String newPassword,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

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

	@GetMapping("user/resendVerificationCode")
	public @ResponseBody Boolean resendVerificationCode(Model model) throws Exception {

		boolean result = false;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();

		}

		if (user.getUserID() != null) {

			EmailConfirmToken pastToken = emailConfirmTokenService.getTokenByUser(user);

			if (pastToken != null)
				emailConfirmTokenService.deleteToken(pastToken);

			if (emailConfirmTokenService.getTokenByUser(user) == null) {

				EmailConfirmToken emailConfirmToken = new EmailConfirmToken(user);
				emailConfirmTokenService.saveToken(emailConfirmToken);
				user.setEmailConfirmToken(emailConfirmToken);

			}

			mailService.resendAccountActivationEmail(user);
			result = true;
		}

		return result;

	}

	@GetMapping("/confirm-email")
	public String UserEmailConfirm(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rAttributes) throws Exception {

		long mills_per_day = 24 * 60 * 60 * 1000L;

		String userID = request.getParameter("uid");
		String token = request.getParameter("token");

		User user = userService.getUserById(userID);

		if (user != null && token != null) {

			EmailConfirmToken emailConfirmToken = emailConfirmTokenService.getTokenByUser(user);

			if (emailConfirmToken != null) {
				Date date = emailConfirmToken.getCreatedDate();

				System.out.println(Math.abs(new Date().getTime() - date.getTime()));

				if (Math.abs(new Date().getTime() - date.getTime()) < mills_per_day) {

					user.setEnabled(true);
					userService.saveUser(user);

					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					if (auth != null) {
						new SecurityContextLogoutHandler().logout(request, response, auth);
					}

				}
			}
		}

		return "redirect:login";
	}

	@GetMapping("/forgot-password")
	public @ResponseBody boolean forgotPassword(@RequestParam("email") String email) {

		boolean result = false;

		try {

			User user = userService.findByEmail(email);

			if (user.getUserID() != null) {

				String password = UniqueIdGenerator.userIDGenerator("pass");
				user.setPassword(passwordEncoder.encode(password));
				result = userService.saveUser(user);

				if (result)
					mailService.passwordRecoveryEmail(user, password);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

}
