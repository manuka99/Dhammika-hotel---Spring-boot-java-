package com.hotel.management.service;

import java.util.List;

import com.hotel.management.model.ContactUs;

public interface ContactUsService {

	boolean saveContactUs(ContactUs contactUs);

	boolean deleteContactUs(String id);

	ContactUs getContactUsById(String id);

	List<ContactUs> getAllContactUs();

}
