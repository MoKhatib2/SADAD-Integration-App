package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{

}
