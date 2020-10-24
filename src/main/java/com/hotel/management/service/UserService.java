package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import com.hotel.management.model.User;

public interface UserService {

	User findByEmail(String email);

	List<User> getAllUsers();

	boolean saveUser(User user);

	User getUserById(String id);

	boolean deleteUserById(String id);

	boolean updateUser(User user);

}