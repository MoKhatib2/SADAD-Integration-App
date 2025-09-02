package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.FusionInvoice;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface FusionInvoiceRepository extends JpaRepository<FusionInvoice, Long>{

}
