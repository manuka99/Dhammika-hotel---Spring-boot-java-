package com.hotel.management.controller.panel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.ContactUs;
import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.Response_Contact;
import com.hotel.management.model.User;
import com.hotel.management.service.ContactUsService;
import com.hotel.management.util.UniqueIdGenerator;

@Controller
public class ContactUsManagement {

	@Autowired
	private ContactUsService contactUsService;

	@GetMapping("/panel/contacts")
	public String getAllContactus(Model model) {

		model.addAttribute("contacts", contactUsService.getAllContactUs());

		return "panel/contact/contactus";
	}

	@PostMapping("/panel/contacts/deleteContact")
	public String deleteProducts(HttpServletRequest request, Model model) {

		String[] cids = {};

		if (request.getParameter("cids[]") != null) {
			cids = request.getParameterValues("cids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		for (String cid : cids) {

			if (contactUsService.deleteContactUs(cid))
				deletedItems.add(cid);

			else
				notDeleted.add(cid);

		}

		if (deletedItems.size() != cids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedItems.size());
		model.addAttribute("countSend", cids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedItems", deletedItems);
		model.addAttribute("displayMessage", true);

		return "panel/contact/contactus";

	}

	@GetMapping("/panel/contacts/contact")
	public String getContactus(@RequestParam("contactUsID") String contactUsID, Model model) {

		try {
			model.addAttribute("contactUs", contactUsService.getContactUsById(contactUsID));

		} catch (Exception e) {
			// TODO: handle exception
		}

		return "panel/contact/contact";
	}

	@PostMapping("/panel/contacts/contact/addCategory")
	public @ResponseBody boolean addResponse(@RequestParam("contactUsID") String contactUsID,
			@RequestParam("message") String message) {

		boolean result = false;

		try {

			ContactUs contact = contactUsService.getContactUsById(contactUsID);

			if (contact.getContactUsID() != null) {

				Response_Contact response = new Response_Contact();
				response.setId(UniqueIdGenerator.userIDGenerator("res"));
				response.setUser(getUserSecurity());
				response.setMessage(message);
				response.setContactus(contact);
				contact.getResponses().add(response);
				result = contactUsService.saveContactUs(contact);

			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@PostMapping("/panel/contacts/response/deleteContact")
	public @ResponseBody boolean deleteResponse(@RequestParam("contactUsID") String contactUsID,
			@RequestParam("responseID") String responseID) {

		boolean result = false;

		try {

			ContactUs contact = contactUsService.getContactUsById(contactUsID);
			Response_Contact responseDelete = new Response_Contact();

			if (contact.getContactUsID() != null) {

				for (Response_Contact response : contact.getResponses()) {

					if (response.getId().equals(responseID))
						responseDelete = response;

				}

				if (responseDelete != null) {
					contact.getResponses().remove(responseDelete);
					result = contactUsService.saveContactUs(contact);
				}

			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	private User getUserSecurity() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
		}

		return user;

	}

}
