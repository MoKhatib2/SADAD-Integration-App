package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.Biller;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface BillerRepository extends JpaRepository<Biller, Long> {

    Optional<Biller> findByCode(String code);

} 