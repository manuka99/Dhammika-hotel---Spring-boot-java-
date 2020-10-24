package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Order_Items;
import com.hotel.management.service.OrderService;

@RestController
public class ChartsController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/panel/charts/ordersPerYear")
	public String getordersCharts(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Date date = new Date();
		LocalDate lDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 6
		// int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // 17
		// int dayOfYear = cal.get(Calendar.DAY_OF_YEAR); //169
		// int month = cal.get(Calendar.MONTH); // 5
		// int year = cal.get(Calendar.YEAR); // 2016

		int yearNow = Year.now().getValue();
		int monthNow = lDate.getMonthValue();

		Double TotalsalesAmount = 0.00;
		int sales = 0;

		Double TotalsalesCompleted = 0.00;
		int totalCompleted = 0;
		Double TotalsalesCancelled = 0.00;
		int totalCancelled = 0;

		try {
			if (request.getParameter("year").equals("") == false)
				yearNow = Integer.parseInt(request.getParameter("year"));

			if (request.getParameter("month").equals("") == false)
				monthNow = Integer.parseInt(request.getParameter("month"));

		} catch (Exception e) {
			// TODO: handle exception
		}

		int[] ordersBymonth = new int[12];
		int[] ordersCompleted = new int[12];
		int[] ordersCancelled = new int[12];
		int[] ordersType = new int[3];
		int[] ordersTypeCompleted = new int[3];

		List<OrderDB> orderList = orderService.getAllOrders(false);

		if (orderList != null) {
			for (OrderDB orderDB : orderList) {

				String input = orderDB.getPlacedDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDate localDate = LocalDate.parse(input, formatter);

				int yearOrder = localDate.getYear();
				int month = localDate.getMonthValue();
				// int dayOfMonth = localDate.getDayOfMonth();

				if (yearNow == yearOrder) {

					ordersBymonth[month - 1] = 1 + ordersBymonth[month - 1];
					TotalsalesAmount += orderDB.getPayment().getTotal();
					++sales;

				}

				if (yearNow == yearOrder && orderDB.getStatus().equals("Delivered")) {

					ordersCompleted[month - 1] = 1 + ordersCompleted[month - 1];
					TotalsalesCompleted += orderDB.getPayment().getTotal();
					++totalCompleted;

				}

				if (yearNow == yearOrder && orderDB.getStatus().equals("Cancelled")) {

					ordersCancelled[month - 1] = 1 + ordersCancelled[month - 1];
					TotalsalesCancelled += orderDB.getPayment().getTotal();
					++totalCancelled;

				}

				if (monthNow == month && yearNow == yearOrder == true) {

					if (orderDB.getOrderType().equals("COD"))
						ordersType[2] = 1 + ordersType[2];

					if (orderDB.getOrderType().equals("paypal"))
						ordersType[0] = 1 + ordersType[0];

					if (orderDB.getOrderType().equals("payhere"))
						ordersType[1] = 1 + ordersType[1];

				}

				if (monthNow == month && yearNow == yearOrder && orderDB.getStatus().equals("Completed")) {

					if (orderDB.getOrderType().equals("COD"))
						ordersTypeCompleted[2] = 1 + ordersTypeCompleted[2];

					if (orderDB.getOrderType().equals("paypal"))
						ordersTypeCompleted[0] = 1 + ordersTypeCompleted[0];

					if (orderDB.getOrderType().equals("Payhere"))
						ordersTypeCompleted[1] = 1 + ordersTypeCompleted[1];

				}

			}

		}

		String json1 = new Gson().toJson(ordersBymonth);
		String json2 = new Gson().toJson(TotalsalesAmount);
		String json3 = new Gson().toJson(sales);

		String json4 = new Gson().toJson(ordersCompleted);
		String json5 = new Gson().toJson(TotalsalesCompleted);
		String json6 = new Gson().toJson(totalCompleted);

		String json7 = new Gson().toJson(ordersCancelled);
		String json8 = new Gson().toJson(TotalsalesCancelled);
		String json9 = new Gson().toJson(totalCancelled);

		String json10 = new Gson().toJson(ordersType);
		String json11 = new Gson().toJson(ordersTypeCompleted);

		String bothJson = "[" + json1 + "," + json2 + "," + json3 + " , " + json4 + " , " + json5 + " , " + json6
				+ " , " + json7 + " , " + json8 + " , " + json9 + " , " + json10 + " , " + json11 + " ]";
		// array of 2 elements

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// System.out.println(Arrays.toString(ordersBymonth));

		return bothJson;
	}

	@GetMapping("/panel/charts/famousProduct")
	public String famousProduct(HttpServletRequest request, HttpServletResponse response) {

		Date date = new Date();
		LocalDate lDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		int yearNow = Year.now().getValue();
		int monthNow = lDate.getMonthValue();

		try {
			if (request.getParameter("year").equals("") == false)
				yearNow = Integer.parseInt(request.getParameter("year"));

			if (request.getParameter("month").equals("") == false)
				monthNow = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<OrderDB> orderList = orderService.getAllOrders(false);

		ArrayList<String> productIDOrderChart = new ArrayList<String>();
		ArrayList<Integer> productQuantityOrderChart = new ArrayList<Integer>();

		Map<String, Integer> mapFood = new HashMap<>();

		if (orderList != null) {
			for (OrderDB orderDB : orderList) {

				String input = orderDB.getPlacedDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDate localDate = LocalDate.parse(input, formatter);

				int yearOrder = localDate.getYear();
				int month = localDate.getMonthValue();

				if (orderDB != null && monthNow == month && yearNow == yearOrder) {

					if (orderDB.getOrder_Items() != null) {

						for (Order_Items item : orderDB.getOrder_Items()) {

							if (mapFood.containsKey(item.getProduct().getName())) {
								Integer valueOfKey = mapFood.get(item.getProduct().getName());
								valueOfKey += item.getQuantity();
								mapFood.put(item.getProduct().getName(), valueOfKey);
							}

							else
								mapFood.put(item.getProduct().getName(), item.getQuantity());

						}

					}

				}
			}
		}

		Set<Map.Entry<String, Integer>> newSetList = mapFood.entrySet();

		for (Map.Entry<String, Integer> mapInt : newSetList) {

			productIDOrderChart.add(mapInt.getKey());
			productQuantityOrderChart.add(mapInt.getValue());

		}

		String json1 = new Gson().toJson(productIDOrderChart);
		String json2 = new Gson().toJson(productQuantityOrderChart);

		String bothJson = "[" + json1 + "," + json2 + "]";

		return bothJson;
	}

}
