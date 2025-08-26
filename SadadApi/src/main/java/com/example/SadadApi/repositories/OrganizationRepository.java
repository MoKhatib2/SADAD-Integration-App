package com.example.SadadApi.repositories;

import org.springframework.stereotype.Repository;
import com.example.SadadApi.models.Organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>{

    Optional<Organization> findByCode(String code);

}
