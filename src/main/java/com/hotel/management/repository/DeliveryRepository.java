package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.DeliveryFee;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryFee, String> {


}