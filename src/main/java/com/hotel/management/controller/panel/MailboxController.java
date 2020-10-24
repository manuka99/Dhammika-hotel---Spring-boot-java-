package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hotel.management.model.Mail;
import com.hotel.management.service.MailBox;
import com.hotel.management.service.MailService;

@Controller
public class MailboxController {

	@Autowired
	private MailService mailService;

	@Autowired
	private MailBox mailBox;

	@GetMapping("/panel/mails")
	private String allMails(Model model) {

		// model.addAttribute("mails", mailBox.getEmails(10, 0, "inbox"));

		return "panel/mailbox/mails";

	}

	@GetMapping("/panel/mails/mail")
	private String getMail(@RequestParam("msgNo") int msgNo, @RequestParam("date") String date,
			@RequestParam("folderName") String folderName, Model model) {

		model.addAttribute("mail", mailBox.getEmail(msgNo, date, folderName));
		return "panel/mailbox/mail";

	}

	@GetMapping("/panel/mails/inbox")
	private String allMailsInbox(@RequestParam(value = "noOfmsgs", required = false) String noMsgs,
			@RequestParam(value = "rows", required = false) String rows,
			@RequestParam(value = "folderName", required = false) String folderName, Model model) {

		int Nsgs = 10;
		int rowsNumber = 0;

		try {
			if (noMsgs != null)
				Nsgs = Integer.parseInt(noMsgs);

			if (rows != null)
				rowsNumber = Integer.parseInt(rows);

			model.addAttribute("folderName", folderName);
			model.addAttribute("mails", mailBox.getEmails(Nsgs, rowsNumber, folderName));
			// model.addAttribute("complains", ComplainService.getAllComplains());

		} catch (Exception e) {

		}

		return "panel/mailbox/mails";

	}

	@PostMapping("/panel/mails/sendMail")
	@ResponseBody
	private Boolean sendMail(@RequestParam("body") String bodyMail, @RequestParam("to") String to,
			@RequestParam("subject") String subject, Model model) throws Exception {

		Mail mail = new Mail();
		mail.setMailTo(to);
		mail.setMailSubject(subject);

		Map<String, Object> object = new HashMap<String, Object>();
		object.put("bodyEmail", bodyMail);
		mail.setModel(object);

		return mailService.sendEmail(mail);

	}

	@GetMapping("/panel/mails/deleteMail")
	@ResponseBody
	private String deleteMail(HttpServletRequest request, Model model) throws Exception {

		String[] msgNos = {};
		String[] times = {};
		String folderName = "";

		if (request.getParameter("msgNos[]") != null) {
			msgNos = request.getParameterValues("msgNos[]");
		}

		if (request.getParameter("times[]") != null) {
			times = request.getParameterValues("times[]");
		}

		if (request.getParameter("folderName") != null) {
			folderName = request.getParameter("folderName");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		int i = 0;
		for (String time : times) {

			try {

				int msgNo = Integer.parseInt(msgNos[i]);

				if (msgNo > 0) {

					System.out.println(time + "\t" + msgNo);
					if (mailBox.deleteMessagesFromDate(folderName, time, msgNo))
						deletedItems.add(time);

					else
						notDeleted.add(time);

				}

				else
					notDeleted.add(time);

			} catch (Exception e) {
				notDeleted.add(time);
			}

			++i;

		}

		String deleted = new Gson().toJson(deletedItems);
		String noDeleted = new Gson().toJson(notDeleted);

		String bothJson = "[" + deleted + "," + noDeleted + " ]";

		return bothJson;

	}

	@PostMapping("/panel/mails/moveMail")
	@ResponseBody
	private String moveMail(HttpServletRequest request, Model model) throws Exception {
		System.out.println("asasasas");
		String[] msgNos = {};
		String[] times = {};
		String folderFrom = "";
		String folderTo = "";

		if (request.getParameter("msgNos[]") != null) {
			msgNos = request.getParameterValues("msgNos[]");
		}

		if (request.getParameter("times[]") != null) {
			times = request.getParameterValues("times[]");
		}

		if (request.getParameter("folderFrom") != null) {
			folderFrom = request.getParameter("folderFrom");
		}

		if (request.getParameter("folderTo") != null) {
			folderTo = request.getParameter("folderTo");
		}

		List<String> notMoved = new ArrayList<>();
		List<String> movedItems = new ArrayList<>();

		System.out.println(times + "\t" + msgNos);

		int i = 0;
		for (String time : times) {

			System.out.println("asasasas");

			try {

				int msgNo = Integer.parseInt(msgNos[i]);

				System.out.println("asasasas");

				if (msgNo > 0) {

					System.out.println("asasasas");

					System.out.println(time + "\t" + msgNo);
					if (mailBox.moveMessagesFromDate(msgNo, time, folderFrom, folderTo))
						movedItems.add(time);

					else
						notMoved.add(time);

				}

				else
					notMoved.add(time);

			} catch (Exception e) {
				e.printStackTrace();
				notMoved.add(time);
			}

			++i;

		}

		String moved = new Gson().toJson(movedItems);
		String noMoved = new Gson().toJson(notMoved);

		String bothJson = "[" + moved + "," + noMoved + " ]";

		return bothJson;

	}

}
