package com.hotel.management.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_db")
public class OrderDB {

	@Id
	@Column(name = "order_id")
	private String orderID;
	private String shippingAddress;
	private String placedDate;
	private String deliveredDate;
	private String expectedDate;
	private String status;
	private String orderType;
	private Date createDate;
	private boolean temp;

	@ManyToOne()
	@JoinColumn(name = "userid")
	private User user;

	@OneToMany(mappedBy = "order_db", cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<Order_Items> order_Items;

	@OneToOne(mappedBy = "order_db", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private PaymentDB payment;

	public OrderDB() {
		super();
		order_Items = new HashSet<>();
		createDate = new Date();
		temp = true;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(String placedDate) {
		this.placedDate = placedDate;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Order_Items> getOrder_Items() {
		return order_Items;
	}

	public void setOrder_Items(Set<Order_Items> order_Items) {
		this.order_Items = order_Items;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

	public PaymentDB getPayment() {
		return payment;
	}

	public void setPayment(PaymentDB payment) {
		this.payment = payment;
	}

}
