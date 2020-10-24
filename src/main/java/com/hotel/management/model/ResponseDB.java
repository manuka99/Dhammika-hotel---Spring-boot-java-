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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.ocpsoft.prettytime.PrettyTime;

@MappedSuperclass
public class ResponseDB {

	@Id
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

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

	@Transient
	private String preetyTime;

	public ResponseDB() {
		super();
		/*
		 * initialize time
		 */
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		this.time = dtf.format(now);
		this.image1=null;
		this.image2=null;
		this.image3=null;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
