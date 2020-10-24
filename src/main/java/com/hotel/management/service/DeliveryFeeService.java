package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import com.hotel.management.model.DeliveryFee;

public interface DeliveryFeeService {

	List<DeliveryFee> getAllDeliveryFees();

	boolean saveDeliveryFee(DeliveryFee deliveryFee);

	DeliveryFee getDeliveryFeeById(String id);
	
	boolean deleteDeliveryFeeById(String id);
	
	double getShippingFeeFromSubTotal(double total);

}
