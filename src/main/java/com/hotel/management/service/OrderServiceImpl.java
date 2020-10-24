package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Order_Items;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;
import com.hotel.management.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CurrencyGeneratorService currencyGeneratorService;
	
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public boolean saveOrder(OrderDB order) {

		boolean result = false;

		try {
			orderRepository.save(order);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public boolean deleteOrderById(String id) {

		boolean result = false;

		try {
			orderRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public boolean updateOrder(OrderDB order) {

		boolean result = false;

		try {
			orderRepository.save(order);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public OrderDB getOrderByIdAndTemp(String id, boolean temp) {

		OrderDB order = new OrderDB();

		try {

			order = orderRepository.findByOrderIDAndTemp(id, temp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return order;
	}

	@Override
	public List<OrderDB> getOrdersByUser(User user, boolean temp) {
		return orderRepository.findByUserAndTemp(user, temp);
	}

	@Override
	public List<OrderDB> getAllOrders(boolean temp) {
		return orderRepository.findAllByTemp(temp);
	}

	@Override
	public boolean hasProductOfUserOrder(Product product, User user) {

		boolean result = false;

		if (user.getUserID() != null) {

			List<OrderDB> orders = getOrdersByUser(user, false);

			if (orders != null) {

				for (OrderDB order : orders) {

					if (order.getStatus().equals("Delivered")) {
						for (Order_Items orderItem : order.getOrder_Items()) {

							try {
								if (orderItem.getProduct().getProductID().equals(product.getProductID())) {
									result = true;
									break;
								}
							} catch (Exception e) {
								// TODO: handle exception
							}

						}
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean deleteOrderTempBasedOnTime() {

		long expireTime = 1 * 60 * 60 * 1000L;

		List<OrderDB> tempOrders = getAllOrders(true);

		if (tempOrders != null) {
			for (OrderDB order_Temp : tempOrders) {

				if (Math.abs(new Date().getTime() - order_Temp.getCreateDate().getTime()) > expireTime)
					deleteOrderById(order_Temp.getOrderID());

			}
		}
		return false;
	}

	@Override
	public OrderDB findByOrderIDAndTempAndUser(String id, boolean temp, User user) {
		OrderDB order = new OrderDB();

		try {

			order = orderRepository.findByOrderIDAndTempAndUser(id, temp, user);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return order;
	}

	@Override
	public OrderDB getOrderInUsd(OrderDB order) {

		double lkrInUsd = currencyGeneratorService.priceOfaUsdToLkr();

		double tax = 0;
		double product_price_total = 0;
		double shipping_fee = 0;
		double total = 0;

		for (Order_Items order_item : order.getOrder_Items()) {

			try {
				order_item.getProduct().setPrice(round(order_item.getProduct().getPrice() / lkrInUsd, 2));
				order_item.getProduct().setTax(round(order_item.getProduct().getTax() / lkrInUsd, 2));
				tax += order_item.getProduct().getTax() * order_item.getQuantity();
				product_price_total += order_item.getProduct().getPrice() * order_item.getQuantity();

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		shipping_fee = round(order.getPayment().getShippingFee() / lkrInUsd, 2);
		total = tax + product_price_total + shipping_fee;

		order.getPayment().setTax(round(tax, 2));
		order.getPayment().setSubTotal(round(product_price_total, 2));
		order.getPayment().setShippingFee(shipping_fee);
		order.getPayment().setTotal(round(total, 2));

		return order;

	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
