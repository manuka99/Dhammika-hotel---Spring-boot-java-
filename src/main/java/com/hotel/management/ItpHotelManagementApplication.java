package com.hotel.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ItpHotelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItpHotelManagementApplication.class, args);
	}

}