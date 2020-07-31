package com.hotel.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.Complain;
import com.hotel.management.model.User;
import com.hotel.management.repository.ComplainsRepository;

@Service
public class ComplainsServiceImpl implements ComplainsService {

	@Autowired
	private ComplainsRepository complainRepository;

	@Override
	public boolean saveComplain(Complain complain) {

		boolean result = false;

		try {
			complainRepository.save(complain);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public boolean updateComplain(Complain complain) {

		boolean result = false;

		try {
			complainRepository.save(complain);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public boolean deleteComplainByid(String id) {

		boolean result = false;

		try {
			complainRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public List<Complain> getAllComplains() {
		return complainRepository.findAll();
	}

	@Override
	public List<Complain> getAllUserComplains(User user) {
		return complainRepository.findByUser(user);
	}

	@Override
	public Complain getUserComplainComplain(String complainID, User user) {
		return complainRepository.findByIdAndUser(complainID, user);
	}

	@Override
	public Complain getComplain(String complainID) {
		return complainRepository.findById(complainID).get();
	}

}
