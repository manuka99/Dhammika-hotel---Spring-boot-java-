package com.hotel.management.service;

import java.util.List;

import com.hotel.management.model.CurrencyGenerator;

public interface CurrencyGeneratorService {

	List<CurrencyGenerator> findAll();

	boolean saveTodb(CurrencyGenerator currencyGenerator);

	double priceOfaUsdToLkr();

	double sendLiveRequest();

}
