package com.hotel.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.management.model.BookEntity;
import com.hotel.management.model.RoomEntity;
import com.hotel.management.service.BookService;
import com.hotel.management.service.RoomService;

@Controller
public class BookingAppController {

	@Autowired
	RoomService service;
	
	@Autowired
	BookService book_service;
	
	@GetMapping("/booking")
	public String getAllRooms(Model model) {
		List<RoomEntity> list = service.getAllRooms();
		model.addAttribute("rooms", list);
		return "booking/list-room";
	}
	
	@RequestMapping(path = {"/booking/edit"})
	public String editRoomById(Model model, @RequestParam(value = "id", required = false) Long id) throws Exception{
		if(id != null) {
			RoomEntity entity = service.getRoomById(id);
			model.addAttribute("room", entity);
		}
		
		else {
			model.addAttribute("room", new RoomEntity());
		}
		return "booking/add-edit-room";
	} 
	
	@GetMapping("/booking/delete")
	public String deleteRoomById(Model model, @RequestParam("id") Long id) throws Exception{
		service.deleteRoomById(id);
		return "redirect:/booking";
	}
	
	@RequestMapping(path= "/booking/createRoom", method = RequestMethod.POST)
	public String createOrUpdateRoom(RoomEntity room) {
		service.createOrUpdateRoom(room);
		return "redirect:/booking";
	}
	
	  @GetMapping("/booking/list-room")
	    public String listroom()
	    {
	        return "redirect:/booking";
	    }
	  
	  @GetMapping("/booking/Booking-list")
	    public String Bookinglist(Model model)
	    {
			List<BookEntity> list = book_service.getAllBooks();
			model.addAttribute("books", list);
	        return "booking/Booking-list";
	    }
}
