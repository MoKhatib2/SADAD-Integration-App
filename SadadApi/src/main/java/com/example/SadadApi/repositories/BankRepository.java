package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long>{

}
