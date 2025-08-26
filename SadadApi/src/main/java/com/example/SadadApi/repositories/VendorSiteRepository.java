package com.example.SadadApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Vendor;
import com.example.SadadApi.models.VendorSite;

@Repository
public interface VendorSiteRepository extends JpaRepository<VendorSite, Long>{

    Optional<Vendor> findByCode(String code);

    @Query("SELECT vs FROM VendorSite vs WHERE vs.vendor.id =:vendorId")
    List<Vendor> findAllByVendorId(@Param("vendorId") Long vendorId);

}
