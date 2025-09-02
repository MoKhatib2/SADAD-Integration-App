package com.example.SadadApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Vendor;

@Repository
@RepositoryRestResource(exported = false)
public interface VendorRepository extends JpaRepository<Vendor, Long>{
    Optional<Vendor> findByCode(String code);

    @Query("SELECT v FROM Vendor v WHERE v.biller.id =:billerId")
    List<Vendor> findAllByBillerId(@Param("billerId") Long billerId);
}
