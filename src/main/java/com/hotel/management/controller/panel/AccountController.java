package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.Role;
import com.hotel.management.model.User;
import com.hotel.management.service.MailService;
import com.hotel.management.service.RoleService;
import com.hotel.management.service.UserService;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder passwordEncorder;

	@Autowired
	private MailService mailService;

	@GetMapping("/panel/accounts")
	public String getUsersByRoles(@RequestParam(value = "roleID", required = false) String roleID, Model model) {

		try {

			if (roleID != null)
				model.addAttribute("role", roleService.getRoleById(roleID));

			else
				model.addAttribute("users", userService.getAllUsers());

		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("roles", roleService.getAllRoles());

		return "panel/account/users";
	}

	@PostMapping("/panel/accounts/addUser")
	public @ResponseBody boolean addUser(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "fname", required = true) String fname,
			@RequestParam(value = "lname", required = true) String lname,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "password", required = true) String password, HttpServletRequest request,
			Model model) {

		boolean result = false;

		User userEmail = userService.findByEmail(email);

		if (userEmail == null) {

			try {

				User user = new User();

				user.setAddress(address);
				user.setEmail(email);
				user.setPhone(Integer.parseInt(phone));
				user.setFname(fname);
				user.setLname(lname);
				user.setPassword(passwordEncorder.encode(password));

				for (String role : request.getParameterValues("roles[]")) {

					try {

						Role roleUser = roleService.getRoleById(role);

						if (roleUser != null) {

							roleUser.getUsers().add(user);
							user.getRoles().add(roleUser);
						}

					} catch (Exception e) {

						e.printStackTrace();

					}

				}

				/*
				 * finally add member
				 */

				try {
					Role roleAdd = roleService.getRoleByName("MEMBER");
					roleAdd.getUsers().add(user);
					user.getRoles().add(roleAdd);
					user.getCart().setUser(user);
					userService.saveUser(user);
					result = true;
				} catch (Exception e) {
					result = false;
				}

			} catch (Exception e) {

				result = false;

			}

			return result;

		} else
			return false;
	}

	@PostMapping("/panel/accounts/deleteAccounts")
	public String deleteAccounts(HttpServletRequest request, Model model) {

		String[] uids = {};

		if (request.getParameter("uids[]") != null) {
			uids = request.getParameterValues("uids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedAccounts = new ArrayList<>();

		for (String uid : uids) {

			if (userService.deleteUserById(uid))
				deletedAccounts.add(uid);

			else
				notDeleted.add(uid);

		}

		if (deletedAccounts.size() != uids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedAccounts.size());
		model.addAttribute("countSend", uids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedProducts", deletedAccounts);
		model.addAttribute("displayMessage", true);

		return "panel/account/users";

	}

	@GetMapping("/panel/accounts/user")
	public String getUserByUserID(@RequestParam(value = "userID", required = false) String userID, Model model) {

		try {

			User user = userService.getUserById(userID);
			List<String> userRole = new ArrayList<>();

			if (user != null) {
				model.addAttribute("user", userService.getUserById(userID));

				for (Role role : user.getRoles()) {

					userRole.add(role.getRoleID());

				}

				model.addAttribute("userRoles", userRole);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("roles", roleService.getAllRoles());

		return "panel/account/user";
	}

	@PostMapping("/panel/accounts/updateUser")
	public @ResponseBody boolean updateUserByUserID(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "fname", required = true) String fname,
			@RequestParam(value = "lname", required = true) String lname,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "dateOfBirth", required = true) String dateOfBirth,
			@RequestParam(value = "userID", required = true) String userID, HttpServletRequest request, Model model) {

		boolean result = false;

		User userEmail = userService.findByEmail(email);

		if (userEmail == null || userEmail.getUserID().equals(userID)) {

			try {

				User user = userService.getUserById(userID);

				if (user.getUserID() != null) {

					user.setAddress(dateOfBirth);
					user.setAddress(address);
					user.setEmail(email);
					user.setPhone(Integer.parseInt(phone));
					user.setFname(fname);
					user.setLname(lname);

					result = userService.saveUser(user);

					if (result)
						mailService.panelUserProfileUpdate(user);

				}
			} catch (Exception e) {

				result = false;

			}

			return result;
		} else
			return false;
	}

	@PostMapping("/panel/accounts/updateRole")
	public @ResponseBody Boolean updateUserRole(@RequestParam("userID") String userID, HttpServletRequest request,
			Model model) {

		Boolean result = false;

		String[] rids = {};

		if (request.getParameter("rids[]") != null) {
			rids = request.getParameterValues("rids[]");
		}

		System.out.println(rids.toString() + "     " + userID);

		try {

			User user = userService.getUserById(userID);

			if (user.getUserID() != null) {

				/*
				 * remove existing roles
				 */
				Set<Role> roleRemoveSet = user.getRoles();

				for (Role roleRemove : roleRemoveSet) {

					roleRemove.getUsers().remove(user);
					roleService.saveRole(roleRemove);

				}

				for (String rid : rids) {

					try {

						Role roleAdd = roleService.getRoleById(rid);
						roleAdd.getUsers().add(user);
						user.getRoles().add(roleAdd);

					} catch (Exception e) {
						result = false;
					}

				}

				/*
				 * finally add member
				 */

				try {
					Role roleAdd = roleService.getRoleByName("MEMBER");
					roleAdd.getUsers().add(user);
					user.getRoles().add(roleAdd);
					userService.saveUser(user);
					result = true;
				} catch (Exception e) {
					result = false;
				}

			}

		} catch (Exception e) {
			result = false;
		}

		return result;

	}

	@PostMapping("/panel/accounts/updateAdvanceSettings")
	public @ResponseBody Boolean updateUserAccountEmailAndLock(@RequestParam("userID") String userID,
			HttpServletRequest request, Model model) {

		Boolean result = false;

		try {

			Boolean enabled = Boolean.parseBoolean(request.getParameter("enabled"));
			Boolean notLocked = Boolean.parseBoolean(request.getParameter("nonLocked"));

			User user = userService.getUserById(userID);

			if (user.getUserID() != null) {

				user.setEnabled(enabled);
				user.setNotLocked(notLocked);
				result = userService.saveUser(user);

				if (result && notLocked == false)
					mailService.panelUserAccountLocked(user);

			}

		} catch (Exception e) {
			result = false;
		}

		return result;

	}

	@PostMapping("/panel/accounts/updatePasword")
	public @ResponseBody Boolean updateUserAccountPassword(@RequestParam("userID") String userID,
			@RequestParam("password") String password, @RequestParam("notify") String notify,
			HttpServletRequest request) {

		Boolean result = false;

		try {

			User user = userService.getUserById(userID);

			if (user.getUserID() != null && password.equals("") == false) {

				user.setPassword(passwordEncorder.encode(password));
				result = userService.saveUser(user);

				if (result) {

					if (notify.equals("email")) {

						mailService.panelUserPasswordUpdate(user);

					}

					else if (notify.equals("emailCredentials")) {
						mailService.panelUserPasswordUpdateWithCredentials(user, password);
					}

				}

			}

		} catch (Exception e) {
			result = false;
		}

		return result;

	}

}
