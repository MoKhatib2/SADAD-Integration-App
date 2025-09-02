package com.example.SadadApi.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.example.SadadApi.models.Organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@RepositoryRestResource(exported = false)
public interface OrganizationRepository extends JpaRepository<Organization, Long>{

    Optional<Organization> findByCode(String code);

}
