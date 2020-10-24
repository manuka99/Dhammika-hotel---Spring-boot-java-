package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import com.hotel.management.model.ContactUs;

public interface ContactUsService {

	boolean saveContactUs(ContactUs contactUs);

	boolean deleteContactUs(String id);

	ContactUs getContactUsById(String id);

	List<ContactUs> getAllContactUs();

}
