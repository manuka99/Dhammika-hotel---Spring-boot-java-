package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	//List<User> findByRoles(Role role);

}
