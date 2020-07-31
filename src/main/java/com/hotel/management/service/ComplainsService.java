package com.hotel.management.service;

import java.util.List;

import com.hotel.management.model.Complain;
import com.hotel.management.model.User;

public interface ComplainsService {

	boolean saveComplain(Complain complain);

	boolean updateComplain(Complain complain);

	boolean deleteComplainByid(String id);

	List<Complain> getAllComplains();

	List<Complain> getAllUserComplains(User user);
	
	Complain getUserComplainComplain(String complainID, User user);
	
	Complain getComplain(String complainID);
}
