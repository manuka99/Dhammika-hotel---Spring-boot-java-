package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;
import java.util.Set;

import com.hotel.management.model.Role;
import com.hotel.management.model.User;

public interface RoleService {
	
	List<Role> getAllRoles();
	boolean saveRole(Role role);
	Role getRoleById(String id);
	boolean deleteRoleById(String id);
	Role getRoleByName(String name);
	Set<User> getUsersByRoleName(List<String> role_names);
}
