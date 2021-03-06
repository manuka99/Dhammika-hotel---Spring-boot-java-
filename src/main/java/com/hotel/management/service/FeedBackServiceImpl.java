package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.management.model.FeedBack;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;
import com.hotel.management.repository.FeedBackRepository;

@Service
public class FeedBackServiceImpl implements FeedBackService {

	private Logger logger = LoggerFactory.getLogger(FeedBackServiceImpl.class);
	
	@Autowired
	private FeedBackRepository feedBackRepository;

	@Override
	public boolean saveFeedback(FeedBack feedback) {

		boolean result = false;

		try {
			feedBackRepository.save(feedback);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@Override
	public boolean updateFeedBack(FeedBack feedback) {

		boolean result = false;

		try {
			feedBackRepository.save(feedback);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@Override
	public boolean deleteFeedBackById(String id) {

		boolean result = false;

		try {
			feedBackRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@Override
	public FeedBack getFeedBackByIdAndUserAndProduct(String id, User user, Product product) {
		return feedBackRepository.findByFeedbackIDAndUserAndProduct(id, user, product);
	}

	@Override
	public List<FeedBack> getAllFedbacksSortTime() {
		Sort sort = Sort.by("time").descending();
		return feedBackRepository.findAll(sort);
	}

	@Override
	public FeedBack getFeedBackById(String id) {
		return feedBackRepository.findById(id).get();
	}

}
