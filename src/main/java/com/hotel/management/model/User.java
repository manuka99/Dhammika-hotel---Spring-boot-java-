package com.hotel.management.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String userID;
	private String fname;
	private String lname;
	private String email;
	private int phone;
	private String password;
	private String dateOfBirth;
	private String address;
	private Boolean notLocked;
	private Boolean enabled;
	@ManyToMany(mappedBy = "users", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private Set<Role> roles;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Cart cart;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	private List<OrderDB> orders;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	private List<Complain> complains;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	private List<Response_Complain> responses;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<NotificationDB> notifications;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	private Set<FeedBack> feedbacks;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private EmailConfirmToken emailConfirmToken;

	@Transient
	private Map<String, OrderDB> LiveOrderList;

	@Transient
	private String username;

	public User() {
		this.enabled = false;
		this.notLocked = true;
		this.cart = new Cart();
		this.roles = new HashSet<>();
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<OrderDB> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDB> orders) {
		this.orders = orders;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Map<String, OrderDB> getLiveOrderList() {
		return LiveOrderList;
	}

	public void setLiveOrderList(Map<String, OrderDB> liveOrderList) {
		LiveOrderList = liveOrderList;
	}

	public List<Complain> getComplains() {
		return complains;
	}

	public void setComplains(List<Complain> complains) {
		this.complains = complains;
	}

	public List<Response_Complain> getResponses() {
		return responses;
	}

	public void setResponses(List<Response_Complain> responses) {
		this.responses = responses;
	}

	public List<NotificationDB> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationDB> notifications) {
		this.notifications = notifications;
	}

	public Set<FeedBack> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<FeedBack> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public String getUsername() {
		return this.fname + " " + this.lname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getNotLocked() {
		return notLocked;
	}

	public void setNotLocked(Boolean notLocked) {
		this.notLocked = notLocked;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public EmailConfirmToken getEmailConfirmToken() {
		return emailConfirmToken;
	}

	public void setEmailConfirmToken(EmailConfirmToken emailConfirmToken) {
		this.emailConfirmToken = emailConfirmToken;
	}

}
