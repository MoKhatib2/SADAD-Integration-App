package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.InvoiceType;

@Repository
public interface InvoiceTypeRepository extends JpaRepository<InvoiceType, Long> {

}
