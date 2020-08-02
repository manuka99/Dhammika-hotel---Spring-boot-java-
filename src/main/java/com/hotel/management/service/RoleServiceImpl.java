package com.hotel.management.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.Role;
import com.hotel.management.model.User;
import com.hotel.management.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public boolean saveRole(Role role) {

		boolean result = false;

		try {
			roleRepository.save(role);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public Role getRoleById(String id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public boolean deleteRoleById(String id) {

		boolean result = false;

		try {
			roleRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Set<User> getUsersByRoleName(List<String> role_names) {

		Set<User> users = new HashSet<>();

		for (String role_name : role_names) {
			try {
				users.addAll(getRoleByName(role_name).getUsers());
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return users;

	}

}
