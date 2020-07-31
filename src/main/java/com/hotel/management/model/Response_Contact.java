package com.hotel.management.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "response_contactus")
public class Response_Contact extends ResponseDB {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private ContactUs contactus;

	public Response_Contact() {
		super();
	}

	public ContactUs getContactus() {
		return contactus;
	}

	public void setContactus(ContactUs contactus) {
		this.contactus = contactus;
	}

}
