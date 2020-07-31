package com.hotel.management.service;

import java.util.List;

import com.hotel.management.model.DeliveryFee;

public interface DeliveryFeeService {

	List<DeliveryFee> getAllDeliveryFees();

	boolean saveDeliveryFee(DeliveryFee deliveryFee);

	DeliveryFee getDeliveryFeeById(String id);
	
	boolean deleteDeliveryFeeById(String id);
	
	double getShippingFeeFromSubTotal(double total);

}
