package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.config.CurrencyUpdateProperties;
import com.hotel.management.model.CurrencyGenerator;
import com.hotel.management.repository.CurrencyGeneratorRepository;

@Service
public class CurrencyGeneratorserviceImpl implements CurrencyGeneratorService {

	private Logger logger = LoggerFactory.getLogger(CurrencyGeneratorserviceImpl.class);
	
	@Autowired
	private CurrencyGeneratorRepository currencyGeneratorRepository;

	@Autowired
	private CurrencyUpdateProperties currencyUpdateProperties;

	static CloseableHttpClient httpClient = HttpClients.createDefault();

	@Override
	public CurrencyGenerator findAll() {

		CurrencyGenerator cg = new CurrencyGenerator();
		List<CurrencyGenerator> cgs = new ArrayList<>();

		try {

			cgs = currencyGeneratorRepository.findAll();

		} catch (Exception e) {
			priceOfaUsdToLkr();

		} finally {

			if (cgs.size() == 1)
				cg = cgs.get(0);

			else if (cgs.size() > 1)
				removeAll();

		}

		return cg;
	}

	@Override
	public boolean saveTodb(CurrencyGenerator currencyGenerator) {

		boolean result = false;

		try {
			currencyGeneratorRepository.save(currencyGenerator);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public double priceOfaUsdToLkr() {

		long expireTime = 3 * 60 * 60 * 1000L;

		double usdValue = 0;

		CurrencyGenerator cg = findAll();

		if (cg.getId() != null) {

			if (Math.abs(new Date().getTime() - cg.getLastUpdate().getTime()) > expireTime) {

				usdValue = sendLiveRequest();

				System.out.println(usdValue + "ssasa");

				if (usdValue != 0) {
					CurrencyGenerator currency = new CurrencyGenerator();
					currency.setId(cg.getId());
					currency.setValue(usdValue);
					saveTodb(currency);
				}

				else
					usdValue = cg.getValue();
			}

			else
				usdValue = cg.getValue();
		}

		else {

			usdValue = sendLiveRequest();
			CurrencyGenerator currency = new CurrencyGenerator();
			currency.setValue(usdValue);
			saveTodb(currency);

		}

		return usdValue;

	}

	@Override
	public double sendLiveRequest() {

		HttpGet get = new HttpGet(currencyUpdateProperties.getUrl() + currencyUpdateProperties.getEndpoint()
				+ "?access_key=" + currencyUpdateProperties.getKey());

		double usdValue = 0;

		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

			usdValue = exchangeRates.getJSONObject("quotes").getDouble("USDLKR");

			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return usdValue;

	}

	@Override
	public boolean removeAll() {

		boolean result = false;

		try {
			currencyGeneratorRepository.deleteAll();
			result = true;
		} catch (Exception e) {
		}

		return result;
	}

}
