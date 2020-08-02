package com.hotel.management.service;

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
		return userRepository.findByEmail(email);
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
		return userRepository.findById(id).get();
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

		try {
			userRepository.save(user);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;
	}

}
