package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import com.hotel.management.model.CurrencyGenerator;

public interface CurrencyGeneratorService {

	CurrencyGenerator findAll();
	
	boolean removeAll();

	boolean saveTodb(CurrencyGenerator currencyGenerator);

	double priceOfaUsdToLkr();

	double sendLiveRequest();

}
