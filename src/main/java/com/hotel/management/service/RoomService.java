package com.hotel.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.RoomEntity;
import com.hotel.management.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository repository;
	
	public List<RoomEntity> getAllRooms(){
		List<RoomEntity> result = (List<RoomEntity>)repository.findAll();
		if(result.size() > 0) {
			return result;
		}
		else {
			return null;
		}
	}
	
	   public RoomEntity getRoomById(Long id) {
	    	try {
	    		return repository.findById(id).get();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
			return new RoomEntity();
	    }
	     
	    public void createOrUpdateRoom(RoomEntity entity) {
	    	try {
	    		repository.save(entity);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	     
	    public void deleteRoomById(Long id) {
	    	try {
	    		repository.deleteById(id);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	}