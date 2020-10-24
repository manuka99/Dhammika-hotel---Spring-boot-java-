package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.CurrencyGenerator;
import com.hotel.management.service.CurrencyGeneratorService;

@Controller
public class CurrencyController {

	@Autowired
	private CurrencyGeneratorService CurrencyGeneratorService;

	@PostMapping("/panel/currency/update")
	public @ResponseBody boolean updateCurrency(@RequestParam("value") String currency) {

		boolean result = false;

		try {
			Double value = Double.parseDouble(currency);
			CurrencyGenerator cg = CurrencyGeneratorService.findAll();
			cg.setLastUpdate(new Date());
			cg.setValue(value);
			if (cg.getId() != null)
				result = CurrencyGeneratorService.saveTodb(cg);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@GetMapping("/panel/currency")
	public String getCurrency(Model model) {

		model.addAttribute("currency", CurrencyGeneratorService.findAll());

		return "panel/currency/currency";

	}

}
