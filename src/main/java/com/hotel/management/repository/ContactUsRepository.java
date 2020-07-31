package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.ContactUs;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, String>{

}
