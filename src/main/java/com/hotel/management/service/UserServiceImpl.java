package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.User;
import com.hotel.management.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public User findByEmail(String email) {
		User user = null;
		try {
			user = userRepository.findByEmail(email);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public boolean saveUser(User user) {

		boolean result = false;

		try {
			userRepository.save(user);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;
	}

	@Override
	public User getUserById(String id) {
		User user = null;
		try {
			user = userRepository.findById(id).get();
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	@Override
	public boolean deleteUserById(String id) {

		boolean result = false;

		try {
			userRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public boolean updateUser(User user) {

		boolean result = false;

		User userEmail = findByEmail(user.getEmail());

		if (userEmail == null || userEmail.getUserID().equals(user.getUserID())) {
			try {
				userRepository.save(user);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception

				result = false;
			}
		}

		return result;
	}

}
