package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.config.ApplicationUrl;
import com.hotel.management.config.PayhereProperties;
import com.hotel.management.config.PaypalConfig;
import com.hotel.management.model.OrderDB;
import com.hotel.management.model.Order_Items;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaymentServicesImpl implements PaymentServices {

	@Autowired
	private PaypalConfig paypalConfig;

	@Autowired
	private PayhereProperties payhereProperties;

	@Autowired
	private ApplicationUrl applicationUrl;

	@Autowired
	private OrderService orderService;
	
	private Logger logger = LoggerFactory.getLogger(PaymentServicesImpl.class);

	public String authorizePaypalPayment(OrderDB orderDetail) throws PayPalRESTException {

		Payer payer = getPayerInformation(orderDetail);
		RedirectUrls redirectUrls = getRedirectURLs();
		List<Transaction> listTransaction = getTransactionInformation(orderDetail);

		Payment requestPayment = new Payment();
		requestPayment.setTransactions(listTransaction);
		requestPayment.setRedirectUrls(redirectUrls);
		requestPayment.setPayer(payer);
		requestPayment.setIntent("authorize");

		Payment approvedPayment = requestPayment.create(paypalConfig.getPaypalContext());
		System.out.println("=== CREATED PAYMENT: ====");
		System.out.println(approvedPayment);

		return getApprovalLink(approvedPayment);

	}

	public Payer getPayerInformation(OrderDB orderDetail) {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(orderDetail.getUser().getFname()).setLastName(orderDetail.getUser().getFname())
				.setEmail(orderDetail.getUser().getEmail());
		// payerInfo.setExternalReuserMeId(orderDetail.getUserID());
		payer.setPayerInfo(payerInfo);

		return payer;
	}

	public RedirectUrls getRedirectURLs() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/Paypal/cancel.jsp");
		redirectUrls.setReturnUrl("http://localhost:8080/reviewPaypalPayment");

		return redirectUrls;
	}

	public List<Transaction> getTransactionInformation(OrderDB orderDetail) {

		Details details = new Details();
		details.setShipping(String.valueOf(orderDetail.getPayment().getShippingFee()));
		details.setSubtotal(String.valueOf(orderDetail.getPayment().getSubTotal()));
		details.setTax(String.valueOf(orderDetail.getPayment().getTax()));

		Amount amount = new Amount();
		amount.setCurrency(orderDetail.getPayment().getCurrency());
		amount.setTotal(String.valueOf(orderDetail.getPayment().getTotal()));
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setInvoiceNumber(orderDetail.getOrderID());

		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<>();

		for (Order_Items orderItem : orderDetail.getOrder_Items()) {
			Item item = new Item();
			item.setCurrency(orderDetail.getPayment().getCurrency());
			item.setName(orderItem.getProduct().getName());
			item.setSku(String.valueOf(orderItem.getProduct().getProductID()));
			item.setPrice(String.valueOf(orderItem.getProduct().getPrice()));
			item.setTax(String.valueOf(orderItem.getProduct().getTax()));
			item.setQuantity(String.valueOf(orderItem.getQuantity()));
			items.add(item);

		}

		itemList.setItems(items);
		transaction.setItemList(itemList);
		itemList.setShippingMethod("");

		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);

		return listTransaction;
	}

	public String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null;

		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
				break;
			}
		}

		return approvalLink;
	}

	public Payment executePaypalPayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment().setId(paymentId);

		return payment.execute(paypalConfig.getPaypalContext(), paymentExecution);
	}

	public Payment getPaypalPaymentDetails(String paymentId) throws PayPalRESTException {
		return Payment.get(paypalConfig.getPaypalContext(), paymentId);
	}

	@Override
	public Map<String, String> authorizePayherePayment(OrderDB orderDetail) {

		Map<String, String> map = new TreeMap<>();

		map.put(payhereProperties.getMode(), "true");
		map.put("merchant_id", payhereProperties.getClientId());
		map.put("return_url", applicationUrl.getUrl2() + "/user/orders/order?orderID=" + orderDetail.getOrderID());
		map.put("cancel_url", applicationUrl.getUrl2() + "/public/payhereCancelUrl");
		map.put("notify_url", applicationUrl.getUrl2() + "/public/payhereNotifyUrl");
		map.put("order_id", orderDetail.getOrderID());
		map.put("items", orderDetail.getOrderID());
		map.put("amount", String.valueOf(orderDetail.getPayment().getTotal()));
		map.put("currency", orderDetail.getPayment().getCurrency());
		map.put("first_name", orderDetail.getUser().getFname());
		map.put("last_name", orderDetail.getUser().getLname());
		map.put("email", orderDetail.getUser().getEmail());
		map.put("phone", String.valueOf(orderDetail.getUser().getPhone()));
		map.put("address", orderDetail.getUser().getAddress());
		map.put("city", "Colombo");
		map.put("country", "Sri Lanka");
		map.put("delivery_address", orderDetail.getShippingAddress());
		map.put("delivery_city", "");
		map.put("delivery_country", "Sri Lanka");

		return map;

	}

}
