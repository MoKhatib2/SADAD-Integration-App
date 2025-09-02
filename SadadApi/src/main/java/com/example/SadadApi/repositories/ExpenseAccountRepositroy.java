package com.example.SadadApi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.ExpenseAccount;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface ExpenseAccountRepositroy extends JpaRepository<ExpenseAccount, Long>{

    Optional<ExpenseAccount> findByCode(String code);

}
