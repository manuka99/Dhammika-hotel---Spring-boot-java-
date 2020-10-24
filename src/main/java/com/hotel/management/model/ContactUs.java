package com.hotel.management.model;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.ocpsoft.prettytime.PrettyTime;

@Entity
@Table(name = "contactus")
public class ContactUs {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String contactUsID;
	private String name;
	private String email;
	private String phone;
	private String subject;
	private String message;
	private String time;

	@OneToMany(mappedBy = "contactus", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Response_Contact> responses;

	@Transient
	private String preetyTime;

	public ContactUs() {
		super();

		/*
		 * initialize time
		 */
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.time = dtf.format(now);
	}

	public List<Response_Contact> getResponses() {
		return responses;
	}

	public void setResponses(List<Response_Contact> responses) {
		this.responses = responses;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContactUsID() {
		return contactUsID;
	}

	public void setContactUsID(String contactUsID) {
		this.contactUsID = contactUsID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPreetyTime() {

		String pTime = "";

		try {
			PrettyTime p = new PrettyTime();
			String input = this.time;
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = format.parse(input);
			pTime = p.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pTime;

	}

	public void setPreetyTime(String preetyTime) {
		this.preetyTime = preetyTime;
	}

}
