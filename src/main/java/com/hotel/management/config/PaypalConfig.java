package com.hotel.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.paypal.base.rest.APIContext;

@Component
@Configuration
public class PaypalConfig {

	@Autowired
	PaypalProperties paypalProperties;

	@Bean
	public APIContext getPaypalContext() {

		APIContext apiContext = new APIContext(paypalProperties.getClientId(), paypalProperties.getClientSecret(),
				paypalProperties.getMode());

		return apiContext;

	}

}