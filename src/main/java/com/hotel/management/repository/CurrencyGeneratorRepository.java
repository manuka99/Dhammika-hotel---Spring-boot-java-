package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.CurrencyGenerator;

@Repository
public interface CurrencyGeneratorRepository extends JpaRepository<CurrencyGenerator, String>{

}
