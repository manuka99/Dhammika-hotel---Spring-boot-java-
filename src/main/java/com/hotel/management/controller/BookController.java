package com.hotel.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.management.model.BookEntity;
import com.hotel.management.service.BookService;
@Controller
public class BookController {
	@Autowired
	BookService service;
	
	@RequestMapping(path = {"/booking/editBook"})
	public String editBookById(Model model, @RequestParam(value = "id", required = false) Long id) throws Exception{
		if(id != null) {
			BookEntity entity = service.getBookById(id);
			model.addAttribute("book", entity);
		}
		
		else {
			model.addAttribute("book", new BookEntity());
		}
		return "booking/Booking";
	} 
	
	@GetMapping("/booking/deleteBook")
	public String deleteBookById(Model model, @RequestParam("id") Long id) throws Exception{
		service.deleteBookById(id);
		return "redirect:/booking";
	}
	
	@RequestMapping(path= "/booking/createBook", method = RequestMethod.POST)
	public String createOrUpdateBook(BookEntity book) {
		service.createOrUpdateBook(book);
		return "redirect:/booking";
	}
}
