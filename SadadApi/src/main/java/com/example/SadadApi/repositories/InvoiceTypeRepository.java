package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.InvoiceType;

@Repository
@RepositoryRestResource(exported = false)
public interface InvoiceTypeRepository extends JpaRepository<InvoiceType, Long> {

}
