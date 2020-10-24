package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.Map;

import com.hotel.management.model.OrderDB;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaymentServices {

	public String authorizePaypalPayment(OrderDB orderDetail) throws PayPalRESTException;

	public Payment executePaypalPayment(String paymentId, String payerId) throws PayPalRESTException;

	public Payment getPaypalPaymentDetails(String paymentId) throws PayPalRESTException;
	
	public Map<String, String> authorizePayherePayment(OrderDB orderDetail);

}
