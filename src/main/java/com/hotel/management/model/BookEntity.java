package com.hotel.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_BOOKING")
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="checkin")
	private String checkin;
	
	@Column(name="checkout")
	private String checkout;
	
	@Column(name="roomType")
	private String roomType;
	
	@Column(name="email")
	private String email;
	
	@Column(name="time")
	private String time;
	
	@Column(name="number")
	private String phoneno;
	
	@Column(name="person")
	private String person;
	
	@Column(name="request")
	private String request;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
    public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	
	@Override
	public String toString() {
		return "Booking[id=" + id + ", Name=" + name + ", checkin = " + checkin + ", checkout =" + checkout + "Room Type" + roomType +
				"Email = " + email + "Time = " + time + "Phone Number =" + phoneno + "Person" + person + "Request = "+ request + "]";
	}
}
