package com.hotel.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.ContactUs;
import com.hotel.management.service.ContactUsService;

@Controller
public class ContactUsController {

	@Autowired
	private ContactUsService contactUsService;

	@PostMapping("/saveContactUs")
	@ResponseBody
	public boolean saveContactUs(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("phone") String phone, @RequestParam("subject") String subject,
			@RequestParam("message") String message) {

		ContactUs contactUs = new ContactUs();
		contactUs.setEmail(email);
		contactUs.setName(name);
		contactUs.setPhone(phone);
		contactUs.setSubject(subject);
		contactUs.setMessage(message);

		return contactUsService.saveContactUs(contactUs);
	}

}
