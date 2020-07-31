package com.hotel.management.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.Role;
import com.hotel.management.repository.UserRepository;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		com.hotel.management.model.User user = userRepository.findByEmail(email);

		// Build user Authority. some how a convert from your custom roles which are in
		// database to spring GrantedAuthority
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

		// The magic is happen in this private method !
		return buildUserForAuthentication(user, authorities);

	}

//Fill your extended User object (CurrentUser) here and return it
	private User buildUserForAuthentication(com.hotel.management.model.User user, List<GrantedAuthority> authorities) {
		String username = user.getFname() + " " + user.getLname();
		String password = user.getPassword();
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = user.getNotLocked();

		CurrentUser currentUser = new CurrentUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);

		/*
		 * com.hotel.management.model.User userOb = new
		 * com.hotel.management.model.User();
		 * 
		 * userOb.setLname(user.getLname()); userOb.setFname(user.getFname());
		 * userOb.setStatus(user.getStatus()); userOb.setEmail(user.getEmail());
		 * userOb.setUserID(user.getUserID()); userOb.setAddress(user.getAddress());
		 * userOb.setDateOfBirth(user.getDateOfBirth());
		 * userOb.setPhone(user.getPhone());
		 */
		currentUser.setUser(user);

		// If your database has more information of user for example firstname,... You
		// can fill it here
		// CurrentUser currentUser = new CurrentUser(....)
		// currentUser.setFirstName( user.getfirstName() );
		// .....
		return currentUser;
	}

	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		return new ArrayList<GrantedAuthority>(setAuths);
	}

}