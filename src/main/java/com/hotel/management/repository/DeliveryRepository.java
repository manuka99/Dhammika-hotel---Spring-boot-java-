package com.hotel.management.repository;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.DeliveryFee;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryFee, String> {


}