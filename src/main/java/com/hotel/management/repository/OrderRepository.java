package com.hotel.management.repository;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.OrderDB;
import com.hotel.management.model.User;

@Repository
public interface OrderRepository extends JpaRepository<OrderDB, String> {

	List<OrderDB> findByUserAndTemp(User user, boolean temp);

	List<OrderDB> findAllByTemp(boolean temp);

	OrderDB findByOrderIDAndTemp(String id, boolean temp);

	OrderDB findByOrderIDAndTempAndUser(String id, boolean temp, User user);
}
