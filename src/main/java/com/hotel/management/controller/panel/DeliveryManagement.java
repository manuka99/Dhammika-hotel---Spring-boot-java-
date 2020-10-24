package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.DeliveryFee;
import com.hotel.management.service.DeliveryFeeService;

@Controller
public class DeliveryManagement {

	@Autowired
	private DeliveryFeeService deliveryService;

	@GetMapping("/panel/delivery/fees")
	public String getDeliveryFees(@RequestParam(value = "feeID", required = false) String feeID, Model model) {

		try {

			if (feeID != null)
				model.addAttribute("fee", deliveryService.getDeliveryFeeById(feeID));

		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("fees", deliveryService.getAllDeliveryFees());

		return "/panel/delivery/fees";
	}

	@GetMapping("/panel/delivery/fee")
	public String getDeliveryFee(@RequestParam("feeID") String feeID, Model model) {

		try {
			model.addAttribute("fees", deliveryService.getDeliveryFeeById(feeID));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "/panel/delivery/fees";
	}

	@PostMapping("/panel/delivery/deleteFee")
	public String deleteDeliveryFee(HttpServletRequest request, Model model) {

		String[] dids = {};

		if (request.getParameter("dids[]") != null) {
			dids = request.getParameterValues("dids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedItems = new ArrayList<>();

		for (String did : dids) {

			if (deliveryService.deleteDeliveryFeeById(did))
				deletedItems.add(did);

			else
				notDeleted.add(did);

		}

		if (deletedItems.size() != dids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedItems.size());
		model.addAttribute("countSend", dids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedItems", deletedItems);
		model.addAttribute("displayMessage", true);

		return "/panel/delivery/fees";
	}

	@PostMapping("/panel/delivery/addFee")
	public @ResponseBody boolean AddFee(@RequestParam("total") Double total, @RequestParam("fee") Double fee,
			Model model) {

		Boolean result = false;

		try {

			DeliveryFee deliveryFee = new DeliveryFee();

			deliveryFee.setTotal(total);
			deliveryFee.setFee(fee);

			result = deliveryService.saveDeliveryFee(deliveryFee);

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

	@PostMapping("/panel/delivery/updateFee")
	public @ResponseBody boolean updateFee(@RequestParam(value = "feeID", required = true) String feeID,
			@RequestParam("total") Double total, @RequestParam("fee") Double fee, Model model) {

		Boolean result = false;

		try {

			DeliveryFee deliveryFee = deliveryService.getDeliveryFeeById(feeID);

			if (deliveryFee.getId() != null) {

				deliveryFee.setTotal(total);
				deliveryFee.setFee(fee);

				result = deliveryService.saveDeliveryFee(deliveryFee);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

}
