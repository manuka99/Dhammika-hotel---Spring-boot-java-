package com.hotel.management.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hotel.management.service.MailService;
import com.hotel.management.service.NotificationService;
import com.hotel.management.util.UniqueIdGenerator;

@Controller
public class ComplainsController {

	@Autowired
	private ComplainsService complainService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MailService mailService;

	private Logger logger = LoggerFactory.getLogger(ComplainsController.class);

	@GetMapping("/user/complains")
	public String userComplains(Model model) {

		model.addAttribute("complains", complainService.getAllUserComplains(getUserSecurity()));

		return "member/profile";

	}

	@PostMapping("/user/addComplain")
	public @ResponseBody boolean userAddComplains(@RequestParam(value = "subject", required = false) String subject,
			@RequestParam(value = "message", required = false) String message,
			@RequestPart(value = "image1", required = false) MultipartFile file1,
			@RequestPart(value = "image2", required = false) MultipartFile file2,
			@RequestPart(value = "image3", required = false) MultipartFile file3, Model model, boolean result)
			throws Exception {

		Complain complain = new Complain();
		complain.setSubject(subject);
		complain.setMessage(message);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		try {
			if (file1 != null)
				complain.setImage1(file1.getBytes());

			if (file2 != null)
				complain.setImage2(file2.getBytes());

			if (file3 != null)
				complain.setImage3(file3.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String inqID = UniqueIdGenerator.userIDGenerator("inq");
		complain.setId(inqID);
		complain.setUser(getUserSecurity());
		complain.setTime(dtf.format(now));
		result = complainService.saveComplain(complain);

		/*
		 * notification
		 */

		if (result) {

			notificationService.NewUserComplain(complain);

			mailService.userPlaceComplain(complain);

		}

		return result;

	}

	@GetMapping("/user/viewComplain")
	public String userViewComplain(@RequestParam("complainID") String complainID, Model model) {

		Response_Complain response = new Response_Complain();

		try {
			Complain complain = complainService.getUserComplainComplain(complainID, getUserSecurity());
			model.addAttribute("complain", complain);
			model.addAttribute("newResponse", response);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exception");
		}

		return "member/profile";

	}

	@PostMapping("/user/addComplainResponse")
	public @ResponseBody boolean userAddComplainResponse(
			@RequestParam(value = "message", required = false) String message,
			@RequestPart(value = "image1", required = false) MultipartFile file1,
			@RequestPart(value = "image2", required = false) MultipartFile file2,
			@RequestPart(value = "image3", required = false) MultipartFile file3,
			@RequestParam("complainID") String complainID, Model model, Boolean result) throws Exception {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		try {

			Complain complain = complainService.getUserComplainComplain(complainID, getUserSecurity());

			if (complain != null) {
				Response_Complain response = new Response_Complain();
				response.setId(UniqueIdGenerator.userIDGenerator("res"));
				response.setMessage(message);
				response.setComplain(complain);
				response.setUser(getUserSecurity());
				response.setTime(dtf.format(now));
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
				result = complainService.saveComplain(complain);

				/*
				 * validate save
				 */

				/*
				 * notification
				 */

				// if (complainService.getUserComplainComplain(complainID,
				// getUserSecurity()).getResponses().iterator()
				// .equals(response)) {

				if (result) {
					if (complain.getUser().getUserID().equals(response.getUser().getUserID())) {

						notificationService.NewUserResponse(response);
						mailService.userComplainPlaceResponse(complain);

					}

					else {

						notificationService.ResponsePlacedByAdminToAdmin(response);

						mailService.userComplainRecieveResponse(complain);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
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
