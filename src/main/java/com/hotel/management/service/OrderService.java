package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;

public interface OrderService {

	/*
	 * temp orders
	 */
	boolean deleteOrderTempBasedOnTime();

	boolean saveOrder(OrderDB order);

	boolean deleteOrderById(String id);

	boolean updateOrder(OrderDB order);

	OrderDB getOrderByIdAndTemp(String id, boolean temp);
	
	OrderDB findByOrderIDAndTempAndUser(String id, boolean temp, User user);

	List<OrderDB> getOrdersByUser(User user, boolean temp);

	List<OrderDB> getAllOrders(boolean temp);

	boolean hasProductOfUserOrder(Product product, User user);
	
	OrderDB getOrderInUsd(OrderDB order);

}
