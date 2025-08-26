package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.Bank;

import java.util.List;
import java.util.Optional;


@Repository
public interface BankRepository extends JpaRepository<Bank, Long>{
    Optional<Bank> findByCode(String code);

    @Query("SELECT b FROM Bank b JOIN b.organizations o WHERE o.id = :organizationId")
    List<Bank> findByOrganizationId(@Param("organizationId") Long organizationId);
}
