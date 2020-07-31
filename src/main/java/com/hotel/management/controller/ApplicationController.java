package com.hotel.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/rest/auth")
public class ApplicationController {
	
	@GetMapping("/process")
	public String process() {
		return "processing..";
	}
	


}
