package com.example.SadadApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Vendor;
import com.example.SadadApi.models.VendorSite;

@Repository
@RepositoryRestResource(exported = false)
public interface VendorSiteRepository extends JpaRepository<VendorSite, Long>{

    Optional<VendorSite> findByCode(String code);

    List<VendorSite> findAllByVendor(Vendor vendor);

}
