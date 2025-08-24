package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.LegalEntity;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long>{

}
