package com.hotel.management.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private com.hotel.management.model.User user;

	// This constructor is a must
	public CurrentUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public com.hotel.management.model.User getUser() {
		return user;
	}

	public void setUser(com.hotel.management.model.User user) {
		this.user = user;
	}

}