package com.hotel.management.repository;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	//@Query("SELECT r FROM Role r WHERE r.name = :name")
	//public Role getRoleByName(@Param("name") String name);
	
	Role findByName(String name);
	
}
