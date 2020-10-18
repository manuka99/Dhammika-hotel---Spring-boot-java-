package com.hotel.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.BookEntity;
import com.hotel.management.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repository;
	
	public List<BookEntity> getAllBooks(){
		List<BookEntity> result = (List<BookEntity>)repository.findAll();
		if(result.size() > 0) {
			return result;
		}
		else {
			return null;
		}
	}
	
	   public BookEntity getBookById(Long id) {
	    	try {
	    		return repository.findById(id).get();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
			return new BookEntity();
	    }
	     
	    public void createOrUpdateBook(BookEntity entity) {
	    	try {
	    		repository.save(entity);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	     
	    public void deleteBookById(Long id) {
	    	try {
	    		repository.deleteById(id);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	}