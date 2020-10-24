package com.hotel.management.model;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "complain")
public class Complain {

	@Id
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "complain", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Response_Complain> responses;

	private String subject;

	private String message;

	@Lob
	private byte[] image1;

	@Lob
	private byte[] image2;

	@Lob
	private byte[] image3;

	private String time;

	@Transient
	List<String> getBase64StringImages;

	public Complain() {
		super();
		getBase64StringImages = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Response_Complain> getResponses() {
		return responses;
	}

	public void setResponses(List<Response_Complain> responses) {
		this.responses = responses;
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

	public byte[] getImage1() {
		return image1;
	}

	public void setImage1(byte[] image1) {
		this.image1 = image1;
	}

	public byte[] getImage2() {
		return image2;
	}

	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}

	public byte[] getImage3() {
		return image3;
	}

	public void setImage3(byte[] image3) {
		this.image3 = image3;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getGetBase64StringImages() {

		List<String> getBase64Strings = new ArrayList<>();

		try {
			getBase64Strings.add(Base64.getEncoder().encodeToString(this.getImage1()));
			getBase64Strings.add(Base64.getEncoder().encodeToString(this.getImage2()));
			getBase64Strings.add(Base64.getEncoder().encodeToString(this.getImage3()));
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getBase64Strings;
	}

	public void setGetBase64StringImages(List<String> getBase64StringImages) {
		this.getBase64StringImages = getBase64StringImages;
	}

}
