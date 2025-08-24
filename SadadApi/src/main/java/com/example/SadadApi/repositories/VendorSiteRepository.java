package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.VendorSite;

@Repository
public interface VendorSiteRepository extends JpaRepository<VendorSite, Long>{

}
