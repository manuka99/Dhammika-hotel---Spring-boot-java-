package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.management.model.Complain;
import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.Response_Complain;
import com.hotel.management.model.User;
import com.hotel.management.service.ComplainsService;
import com.hotel.management.util.UniqueIdGenerator;

@Controller
public class ComplainsControllerPanel {

	@Autowired
	private ComplainsService ComplainService;

	@GetMapping("/panel/complains")
	public String getAllComplains(Model model) {

		model.addAttribute("complains", ComplainService.getAllComplains());

		return "panel/complain/complains";
	}

	@PostMapping("/panel/complains/deletecomplain")
	public String deleteComplains(HttpServletRequest request, Model model) {

		String[] cids = {};

		if (request.getParameter("cids[]") != null) {
			cids = request.getParameterValues("cids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		for (String cid : cids) {

			if (ComplainService.deleteComplainByid(cid))
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

		return "panel/complain/complains";

	}

	@GetMapping("/panel/complains/complain")
	public String getComplain(@RequestParam("ComplainID") String ComplainID, Model model) {

		try {
			model.addAttribute("complain", ComplainService.getComplain(ComplainID));

		} catch (Exception e) {
			// TODO: handle exception
		}

		return "panel/complain/complain";
	}

	@PostMapping("/panel/complains/complain/addResponse")
	public @ResponseBody boolean addResponse(@RequestParam("complainID") String ComplainID,
			@RequestPart(value = "image1", required = false) MultipartFile file1,
			@RequestPart(value = "image2", required = false) MultipartFile file2,
			@RequestPart(value = "image3", required = false) MultipartFile file3,
			@RequestParam("message") String message) {

		boolean result = false;

		try {

			Complain complain = ComplainService.getComplain(ComplainID);

			if (complain.getId() != null) {

				Response_Complain response = new Response_Complain();
				response.setId(UniqueIdGenerator.userIDGenerator("res"));
				response.setUser(getUserSecurity());
				response.setMessage(message);
				response.setComplain(complain);
				try {
					if (file1 != null)
						response.setImage1(file1.getBytes());

					if (file2 != null)
						response.setImage2(file2.getBytes());

					if (file3 != null)
						response.setImage3(file3.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				complain.getResponses().add(response);
				result = ComplainService.saveComplain(complain);

			}

		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	@PostMapping("/panel/complains/response/deleteResponse")
	public @ResponseBody boolean deleteResponse(@RequestParam("ComplainID") String ComplainID,
			@RequestParam("responseID") String responseID) {

		boolean result = false;

		try {

			Complain complain = ComplainService.getComplain(ComplainID);
			Response_Complain responseDelete = new Response_Complain();

			if (complain.getId() != null) {

				for (Response_Complain response : complain.getResponses()) {

					if (response.getId().equals(responseID))
						responseDelete = response;

				}

				if (responseDelete != null) {
					complain.getResponses().remove(responseDelete);
					result = ComplainService.saveComplain(complain);
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
