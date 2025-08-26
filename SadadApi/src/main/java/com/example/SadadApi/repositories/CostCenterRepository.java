package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.CostCenter;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long>{

    Optional<CostCenter> findByCode(String code);
    
}