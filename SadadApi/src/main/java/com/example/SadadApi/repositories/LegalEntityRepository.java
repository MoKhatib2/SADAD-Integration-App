package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.LegalEntity;

@Repository
@RepositoryRestResource(exported = false)
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long>{

    Optional<LegalEntity> findByCode(String code);

}
