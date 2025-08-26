package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

    Optional<Location> findByCode(String code);

}
