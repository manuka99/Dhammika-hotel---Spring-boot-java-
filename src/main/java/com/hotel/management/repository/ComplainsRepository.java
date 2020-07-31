package com.hotel.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.Complain;
import com.hotel.management.model.User;

@Repository
public interface ComplainsRepository extends JpaRepository<Complain, String>{
	
	List<Complain> findByUser(User user);
	Complain findByIdAndUser(String complainID, User user);
	
}
