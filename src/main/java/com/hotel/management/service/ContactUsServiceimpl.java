package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.ContactUs;
import com.hotel.management.repository.ContactUsRepository;

@Service
public class ContactUsServiceimpl implements ContactUsService {
	
	private Logger logger = LoggerFactory.getLogger(ContactUsServiceimpl.class);

	@Autowired
	private ContactUsRepository contactUsRepository;

	@Override
	public boolean saveContactUs(ContactUs contactUs) {

		boolean result = false;

		try {
			contactUsRepository.save(contactUs);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	@Override
	public boolean deleteContactUs(String id) {

		boolean result = false;

		try {
			contactUsRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@Override
	public ContactUs getContactUsById(String id) {
		return contactUsRepository.findById(id).get();
	}

	@Override
	public List<ContactUs> getAllContactUs() {
		return contactUsRepository.findAll();
	}

}
