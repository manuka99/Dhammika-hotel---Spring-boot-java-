package com.hotel.management.service;

import com.hotel.management.model.CurrencyGenerator;

public interface CurrencyGeneratorService {

	CurrencyGenerator findAll();
	
	boolean removeAll();

	boolean saveTodb(CurrencyGenerator currencyGenerator);

	double priceOfaUsdToLkr();

	double sendLiveRequest();

}
