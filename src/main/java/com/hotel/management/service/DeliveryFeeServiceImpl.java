package com.hotel.management.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.DeliveryFee;
import com.hotel.management.repository.DeliveryRepository;

@Service
public class DeliveryFeeServiceImpl implements DeliveryFeeService {

	private Logger logger = LoggerFactory.getLogger(DeliveryFeeServiceImpl.class);
	
	@Autowired
	private DeliveryRepository deliveryRepository;

	@Override
	public List<DeliveryFee> getAllDeliveryFees() {
		return deliveryRepository.findAll();
	}

	@Override
	public boolean saveDeliveryFee(DeliveryFee deliveryFee) {
		
		boolean result = false;

		try {
			deliveryRepository.save(deliveryFee);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public DeliveryFee getDeliveryFeeById(String id) {
		return deliveryRepository.findById(id).get();
	}

	@Override
	public boolean deleteDeliveryFeeById(String id) {

		boolean result = false;

		try {
			deliveryRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;
		
	}
	
	public double getShippingFeeFromSubTotal(double total) {
		
		List<DeliveryFee> Dfees = deliveryRepository.findAll();
		
		double fee = 0;
		
		if (Dfees != null) {
			Map<Double, Double> fees = new TreeMap<>();

			for (DeliveryFee deliveryFee : Dfees) {

				fees.put(deliveryFee.getTotal(), deliveryFee.getFee());

			}
			
			System.out.println(fees.toString());

			for (Double dbTotal : fees.keySet()) {

				System.out.println(fee);

				if (total >= dbTotal)
					fee = fees.get(dbTotal);

			}
		}
		return fee;
	}

}
