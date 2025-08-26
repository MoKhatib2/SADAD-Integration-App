package com.example.SadadApi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    @Query("SELECT ba FROM BankAccount ba WHERE ba.bank.id =:bankId")
    List<BankAccount> findByBankId(@Param("bankId") Long bankId);

    Optional<BankAccount> findByIban(String iban);

    @Query("SELECT ba FROM BankAccount ba WHERE ba.bank.id =:bankId AND ba.legalEntity.id =:legalEntityId")
    List<BankAccount> findByBankIdAndLegalEntityId(@Param("bankId") Long bankId, @Param("legalEntityId") Long legalEntityId);
}
