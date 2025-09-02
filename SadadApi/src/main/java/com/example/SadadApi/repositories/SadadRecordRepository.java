package com.example.SadadApi.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SadadApi.models.SadadRecord;
import com.example.SadadApi.models.SadadRecord.SadadStatus;

@Repository
@RepositoryRestResource(exported = false)
public interface SadadRecordRepository extends JpaRepository<SadadRecord, Long> {
    @Query("""
        SELECT DISTINCT sr
        FROM SadadRecord sr
        LEFT JOIN FETCH sr.allocations sc
        LEFT JOIN FETCH sc.costCenter cc
    """)
    List<SadadRecord> findAllWithAllocationsAndCostCenters();

    @Query("""
        SELECT DISTINCT sr
        FROM SadadRecord sr
        LEFT JOIN FETCH sr.allocations sc
        LEFT JOIN FETCH sc.costCenter cc
        WHERE sr.id =:id
    """)
    Optional<SadadRecord> findAllWithAllocationsAndCostCentersById(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT sr
        FROM SadadRecord sr
        LEFT JOIN FETCH sr.organization o
        LEFT JOIN FETCH sr.legalEntity le
        LEFT JOIN FETCH sr.remitterBank rb
        LEFT JOIN FETCH sr.bankAccount ba
        LEFT JOIN FETCH sr.biller b
        LEFT JOIN FETCH sr.vendor v
        LEFT JOIN FETCH sr.vendorSite vs
        LEFT JOIN FETCH sr.invoiceType it
        LEFT JOIN FETCH sr.expenseAccount ea
        LEFT JOIN FETCH sr.business bus
        LEFT JOIN FETCH sr.location loc
        LEFT JOIN FETCH sr.allocations sc
        LEFT JOIN FETCH sc.costCenter cc
    """)
    List<SadadRecord> findAllWithAllRelations();

    @Query("""
        SELECT DISTINCT sr
        FROM SadadRecord sr
        LEFT JOIN FETCH sr.organization o
        LEFT JOIN FETCH sr.legalEntity le
        LEFT JOIN FETCH sr.remitterBank rb
        LEFT JOIN FETCH sr.bankAccount ba
        LEFT JOIN FETCH sr.biller b
        LEFT JOIN FETCH sr.vendor v
        LEFT JOIN FETCH sr.vendorSite vs
        LEFT JOIN FETCH sr.invoiceType it
        LEFT JOIN FETCH sr.expenseAccount ea
        LEFT JOIN FETCH sr.business bus
        LEFT JOIN FETCH sr.location loc
        LEFT JOIN FETCH sr.allocations sc
        LEFT JOIN FETCH sc.costCenter cc
        WHERE sr.id = :id
    """)
    Optional<SadadRecord> findAllDataById(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT sr
        FROM SadadRecord sr
        LEFT JOIN FETCH sr.allocations sc
        LEFT JOIN FETCH sc.costCenter cc
        LEFT JOIN FETCH sr.organization o
        LEFT JOIN FETCH sr.remitterBank b
        LEFT JOIN FETCH sr.expenseAccount ea
        WHERE sr.status = :status
    """)
    Optional<SadadRecord> findAllByStatus(@Param("status") SadadStatus status);

}
