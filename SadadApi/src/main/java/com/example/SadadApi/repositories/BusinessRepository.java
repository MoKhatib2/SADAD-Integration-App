package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.Business;

public interface BusinessRepository extends JpaRepository<Business, Long>{

    Optional<Business> findByCode(String code);

}
