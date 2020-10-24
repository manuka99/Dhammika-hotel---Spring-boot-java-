package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import com.hotel.management.model.FeedBack;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;

public interface FeedBackService {

	boolean saveFeedback(FeedBack feedack);

	boolean updateFeedBack(FeedBack feedback);

	boolean deleteFeedBackById(String id);

	FeedBack getFeedBackByIdAndUserAndProduct(String id, User user, Product product);
	
	FeedBack getFeedBackById(String id);

	List<FeedBack> getAllFedbacksSortTime();
}
