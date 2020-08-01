package com.hotel.management.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.hotel.management.model.Cart;
import com.hotel.management.model.Cart_Items;
import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Order_Items;
import com.hotel.management.model.PaymentDB;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;
import com.hotel.management.service.CartService;
import com.hotel.management.service.CurrencyGeneratorService;
import com.hotel.management.service.DeliveryFeeService;
import com.hotel.management.service.MailService;
import com.hotel.management.service.NotificationService;
import com.hotel.management.service.OrderService;
import com.hotel.management.service.PaymentServices;
import com.hotel.management.service.ProductService;
import com.hotel.management.util.UniqueIdGenerator;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ProductService productService;

	@Autowired
	private DeliveryFeeService deliveryFeeService;

	@Autowired
	private MailService mailService;

	@Autowired
	private CurrencyGeneratorService currencyGeneratorService;

	@Autowired
	private PaymentServices paymentServices;

	@GetMapping("/user/checkout")
	public String Checkout(Model model) {

		orderService.deleteOrderTempBasedOnTime();

		String url = "/error";

		User user = getSessionUser();

		OrderDB orderDetail = new OrderDB();
		PaymentDB payment = new PaymentDB();

		try {

			if (cartService.calculateUpdateCartValues(cartService.getCartByUserId(user))) {

				Cart myCart = cartService.getCartByUserId(user);

				String orderID = UniqueIdGenerator.userIDGenerator("ord");
				orderDetail.setOrderID(orderID);
				orderDetail.setShippingAddress(user.getAddress());
				payment.setShippingFee(myCart.getShippingFee());
				payment.setTax(myCart.getTax());
				payment.setSubTotal(myCart.getProductPriceTotal());
				payment.setTotal(myCart.getTotal());
				orderDetail.setUser(user);

				for (Cart_Items cart_item : myCart.getCart_Items()) {
					Order_Items order_item = new Order_Items();
					order_item.setProduct(cart_item.getProduct());
					order_item.setQuantity(cart_item.getQuantity());
					order_item.setOrder_db(orderDetail);
					orderDetail.getOrder_Items().add(order_item);
				}

				payment.setOrder(orderDetail);
				orderDetail.setPayment(payment);

				orderService.saveOrder(orderDetail);

				url = "redirect:/payment/CheckoutV2?orderID=" + orderID;

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return url;
	}

	@GetMapping("/user/buyNow")
	public String BuyNow(@RequestParam("productID") String productID, @RequestParam("quantity") String quantity,
			Model model) {

		User user = getSessionUser();

		int intQty = 1;

		try {
			intQty = Integer.parseInt(quantity);
		} catch (Exception e) {
			intQty = 1;
		}

		Product product = productService.getProductById(productID);

		OrderDB orderDetail = new OrderDB();
		com.hotel.management.model.PaymentDB payment = new com.hotel.management.model.PaymentDB();

		String orderID = UniqueIdGenerator.userIDGenerator("ord");
		orderDetail.setOrderID(orderID);
		orderDetail.setUser(user);
		orderDetail.setShippingAddress(user.getAddress());
		payment.setTax(product.getTax() * intQty);
		payment.setSubTotal(product.getPrice() * intQty);
		payment.setShippingFee(deliveryFeeService.getShippingFeeFromSubTotal(payment.getSubTotal()));
		payment.setTotal(payment.getTax() + payment.getSubTotal() + payment.getShippingFee());

		Order_Items order_item = new Order_Items();
		order_item.setProduct(product);
		order_item.setQuantity(intQty);
		order_item.setOrder_db(orderDetail);
		orderDetail.getOrder_Items().add(order_item);

		payment.setOrder(orderDetail);
		orderDetail.setPayment(payment);

		orderService.saveOrder(orderDetail);
		model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());

		return "redirect:/payment/CheckoutV2?orderID=" + orderID;
	}

	@GetMapping("/payment/CheckoutV2")
	public String showUserCheckoutForward(@RequestParam("orderID") String orderID, Model model) {

		model.addAttribute("orderDetails", orderService.getOrderByIdAndTemp(orderID, true));
		model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());

		return "payment/checkout";
	}

	@PostMapping("/user/placeOrder")
	public String placeOrder(HttpServletRequest request) {

		String orderID = request.getParameter("orderID");
		String deliveryDate = request.getParameter("dDate");
		String orderType = request.getParameter("orderType");

		String returnUrl = "/error";

		OrderDB order = orderService.getOrderByIdAndTemp(orderID, true);

		if (order != null) {

			order.setShippingAddress(order.getUser().getAddress());

			if (request.getParameter("address2") != null && request.getParameter("address2").length() > 0)
				order.setShippingAddress(request.getParameter("address2"));

			if (deliveryDate != null && deliveryDate.length() > 0)
				order.setExpectedDate(deliveryDate);

			boolean valid = true;

			if (orderType.equals("cod"))
				order.getPayment().setCurrency("LKR");

			else if (request.getParameter("currency") != null) {

				order.getPayment().setCurrency(request.getParameter("currency"));

				valid = false;

				if (request.getParameter("currency").equals("USD") && orderType.equals("paypal"))
					valid = true;

				else if ((request.getParameter("currency").equals("USD")
						|| request.getParameter("currency").equals("LKR")) && orderType.equals("payhere"))
					valid = true;

			}

			if (valid) {

				orderService.updateOrder(order);

				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);

				if (orderType.equals("paypal"))
					returnUrl = "redirect:/user/paypalPayment";

				else if (orderType.equals("payhere"))
					returnUrl = "redirect:/user/payherePayment";

				else if (orderType.equals("cod"))
					returnUrl = "redirect:/user/cashOnDelivery";

			}
		}

		return returnUrl;

	}

	@PostMapping("/user/cashOnDelivery")
	public String placeCODOrder(@RequestParam("orderID") String orderID, HttpServletRequest request) throws Exception {

		OrderDB order = orderService.getOrderByIdAndTemp(orderID, true);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		if (order != null) {

			order.setTemp(false);
			order.setPlacedDate(dtf.format(now));
			order.setStatus("Processing");
			order.setOrderType("COD");
			order.getPayment().setPaymentStatus("Not Paid");
			order.getPayment().setCurrency("LKR");

			boolean result = orderService.saveOrder(order);

			/*
			 * send notification
			 */

			// validate order saved
			if (result) {

				notificationService.NewUserOrder(order);

				mailService.orderPlacedEmail(order);

			}

		}

		return "redirect:/user/order?orderID=" + order.getOrderID();
	}

	@PostMapping("/user/payherePayment")
	@ResponseBody
	public Map<String, String> placePayhereOrderJS(@RequestParam("orderID") String orderID) {

		OrderDB order = orderService.getOrderByIdAndTemp(orderID, true);

		if (order.getPayment().getCurrency().equals("USD"))
			order = orderService.getOrderInUsd(order);

		return paymentServices.authorizePayherePayment(order);
	}

	@PostMapping("/user/paypalPayment")
	public ModelAndView payPaypal(@RequestParam("orderID") String orderID) {

		ModelAndView modelAndView = new ModelAndView();

		OrderDB order = orderService.getOrderByIdAndTemp(orderID, true);

		if (order != null) {

			try {
				String approvalLink = paymentServices.authorizePaypalPayment(orderService.getOrderInUsd(order));

				modelAndView.setViewName("redirect:" + approvalLink);

			} catch (PayPalRESTException ex) {
				modelAndView.addObject("errorMessage", ex.getMessage());
				ex.printStackTrace();
				modelAndView.setViewName("redirect:/error");
			}
		}

		return modelAndView;

	}

	@GetMapping("/reviewPaypalPayment")
	public String reviewPaypalPayment(@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String PayerID, Model model) {

		String url = "";

		try {
			Payment payment = paymentServices.getPaypalPaymentDetails(paymentId);

			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();

			model.addAttribute("items", transaction.getItemList().getItems());
			model.addAttribute("payer", payerInfo);
			model.addAttribute("transaction", transaction);
			model.addAttribute("shippingAddress", shippingAddress);

			model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());

			url = "payment/review";

		} catch (PayPalRESTException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			url = "error";
		}

		return url;

	}

	@PostMapping("/executePaypalPayemnt")
	public String executePaypalPayment(@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String PayerID, Model model) {

		String url = "";

		try {

			Payment payment = paymentServices.getPaypalPaymentDetails(paymentId);

			Transaction transaction = payment.getTransactions().get(0);

			OrderDB order = orderService.getOrderByIdAndTemp(transaction.getInvoiceNumber(), true);

			if (order.getOrderID() != null) {

				Payment paymentState = paymentServices.executePaypalPayment(paymentId, PayerID);

				// PayerInfo payerInfo = paymentState.getPayer().getPayerInfo();
				Transaction transactionPayment = paymentState.getTransactions().get(0);

				if (paymentState.getState().equals("approved")) {

					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();

					order.setTemp(false);
					order.setPlacedDate(dtf.format(now));
					order.setStatus("Processing");
					order.setOrderType("Paypal");
					order.getPayment().setPaymentStatus("Paid");
					order.getPayment().setTransactionID(paymentState.getId());

					order.getPayment()
							.setShippingFee(Double.valueOf(transactionPayment.getAmount().getDetails().getShipping()));
					order.getPayment()
							.setSubTotal(Double.valueOf(transactionPayment.getAmount().getDetails().getSubtotal()));
					order.getPayment().setTax(Double.valueOf(transactionPayment.getAmount().getDetails().getTax()));
					order.getPayment().setTotal(Double.valueOf(transactionPayment.getAmount().getTotal()));

					boolean result = orderService.saveOrder(order);

					/*
					 * send notification
					 */

					// validate order saved
					if (result) {

						notificationService.NewUserOrder(order);

						mailService.orderPlacedEmail(order);

					}

					url = "redirect:/user/order?orderID=" + order.getOrderID();
				}
			}

		} catch (Exception ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			url = "error.jsp";
		}

		return url;

	}

	@PostMapping("/public/payhereNotifyUrl")
	public void payhereNotifyUrl(HttpServletRequest request) throws Exception {

		String merchant_id = request.getParameter("merchant_id");
		String order_id = request.getParameter("order_id");
		String payhere_amount = request.getParameter("payhere_amount");
		String payhere_currency = request.getParameter("payhere_currency");
		String status_code = request.getParameter("status_code");
		String md5sig = request.getParameter("md5sig");
		String method = request.getParameter("method ");
		String status = "";

		if (status_code.equals("0"))
			status = "pending";

		else if (status_code.equals("-3"))
			status = "chargedback";

		else if (status_code.equals("2"))
			status = "success";

		else if (status_code.equals("-1"))
			status = "canceled";

		else
			status = "failed";

		if (status.equals("success")) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();

			OrderDB order = orderService.getOrderByIdAndTemp(order_id, true);

			order.setTemp(false);
			order.setPlacedDate(dtf.format(now));
			order.setStatus("Processing");
			order.setOrderType("payhere");
			order.getPayment().setPaymentStatus("Paid");

			if (payhere_currency.equals("USD")) {

				double payhereAmmount = Double.parseDouble(payhere_amount);

				double usdCurrency = order.getPayment().getTotal() / payhereAmmount;

				order.getPayment().setShippingFee(order.getPayment().getShippingFee() / usdCurrency);
				order.getPayment().setSubTotal(order.getPayment().getSubTotal() / usdCurrency);
				order.getPayment().setTax(order.getPayment().getTax() / usdCurrency);
				order.getPayment().setTotal(order.getPayment().getTotal() / usdCurrency);

			}

			boolean result = orderService.saveOrder(order);

			/*
			 * send notification
			 */

			// validate order saved
			if (result) {

				notificationService.NewUserOrder(order);

				mailService.orderPlacedEmail(order);

			}

		}

	}

	@GetMapping("/user/orders")
	public String listMyOrders(Model model) {

		List<OrderDB> orders = orderService.getOrdersByUser(getSessionUser(), false);

		model.addAttribute("orders", orders);

		return "member/profile";
	}

	@GetMapping("/user/order")
	public String getOrderDetails(@RequestParam("orderID") String orderID, Model model) {

		OrderDB order = orderService.findByOrderIDAndTempAndUser(orderID, false, getSessionUser());

		if (order.getOrderID() != null)
			model.addAttribute("order", order);

		model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());

		return "member/order";
	}

	@GetMapping("/user/cancelOrder")
	public String cancelOrder(@RequestParam("orderID") String orderID, Model model) throws Exception {

		List<OrderDB> orders = orderService.getOrdersByUser(getSessionUser(), false);

		OrderDB order = new OrderDB();

		for (OrderDB orderDB : orders) {

			if (orderDB.getOrderID().equals(orderID)) {

				order = orderDB;

			}

		}

		if (order != null && order.getStatus().equals("Processing")) {

			order.setStatus("Cancelled");
			boolean result = orderService.updateOrder(order);

			if (result) {
				notificationService.UpdateUserOrder(order);
				mailService.orderStatusEmail(order);
			}

		}

		return "redirect:/user/order?orderID=" + order.getOrderID();
	}

	public User getSessionUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
		}

		return user;

	}

}
