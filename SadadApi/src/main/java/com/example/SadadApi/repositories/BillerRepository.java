package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.Biller;

public interface BillerRepository extends JpaRepository<Biller, Long> {

    Optional<Biller> findByCode(String code);

} 