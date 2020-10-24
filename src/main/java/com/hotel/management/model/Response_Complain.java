package com.hotel.management.model;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "response_complain")
public class Response_Complain extends ResponseDB {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "complain_id")
	private Complain complain;

	public Response_Complain() {
		super();
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

}
