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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.Role;
import com.hotel.management.model.User;
import com.hotel.management.service.RoleService;

@Controller
public class RoleManagement {

	@Autowired
	private RoleService roleService;

	@GetMapping("/panel/roles")
	private String listRoles(@RequestParam(value = "roleID", required = false) String roleID, Model model) {

		try {

			if (roleID != null)
				model.addAttribute("role", roleService.getRoleById(roleID));

		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("roles", roleService.getAllRoles());
		return "panel/role/roles";

	}

	@PostMapping("/panel/roles/addRole")
	public @ResponseBody boolean addRole(@RequestParam("name") String name,
			@RequestParam("description") String description) {

		Role role = new Role();
		role.setDescription(description);
		role.setName(name);

		return roleService.saveRole(role);
	}

	@PostMapping("/panel/roles/deleteRole")
	public String deleteRoles(HttpServletRequest request, Model model) {

		String[] rids = {};

		if (request.getParameter("rids[]") != null) {
			rids = request.getParameterValues("rids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		for (String rid : rids) {

			Role role = new Role();
			try {
				role = roleService.getRoleById(rid);
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (role.getRoleID() != null) {

				Set<User> RoleUsers = role.getUsers();

				role.getUsers().removeAll(RoleUsers);

				if (roleService.saveRole(role)) {

					if (roleService.deleteRoleById(rid))
						deletedItems.add(rid);

					/*
					 * role not deleted add users back and save
					 */
					else {
						role.setUsers(RoleUsers);
						roleService.saveRole(role);
						notDeleted.add(rid);
					}

				}

				else {
					role.setUsers(RoleUsers);
					roleService.saveRole(role);
				}
			}
		}

		if (deletedItems.size() != rids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedItems.size());
		model.addAttribute("countSend", rids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedItems", deletedItems);
		model.addAttribute("displayMessage", true);

		return "panel/role/roles";
	}

	@PostMapping("/panel/roles/updateRole")
	public @ResponseBody boolean updateRole(@RequestParam("roleID") String roleID, @RequestParam("name") String name,
			@RequestParam("description") String description) {

		Role role = new Role();
		
		boolean result = true;

		try {
			role = roleService.getRoleById(roleID);

			if (role.getRoleID() != null) {

				role.setDescription(description);
				role.setName(name);
				result = roleService.saveRole(role);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}
}
