package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.BankAccountDto;
import com.example.SadadApi.models.Bank;
import com.example.SadadApi.models.BankAccount;
import com.example.SadadApi.models.LegalEntity;
import com.example.SadadApi.repositories.BankAccountRepository;
import com.example.SadadApi.repositories.BankRepository;
import com.example.SadadApi.repositories.LegalEntityRepository;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.BankAccountService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BankAccountServiceImpl implements BankAccountService{
    final private BankAccountRepository bankAccountRepository;
    final private BankRepository bankRepository;
    final private LegalEntityRepository legalEntityRepository;

    @Override
    public GenericResponse<BankAccountDto> create(BankAccountDto bankAccountDto) {
        Optional<BankAccount> existingBankAccount = bankAccountRepository.findByIban(bankAccountDto.iban());
        if (existingBankAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another bank using this code");
        }
        Bank bank = bankRepository.findById(bankAccountDto.BankId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank not found")); 

        LegalEntity legalEntity = legalEntityRepository.findById(bankAccountDto.legalEntityId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found")); 

        existingBankAccount = bankAccountRepository.findByAccountNumber(bankAccountDto.accountNumber());
        if (existingBankAccount.isPresent() && existingBankAccount.get().getBank().getId().equals(bank.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another account with same account number within same bank");
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(bankAccountDto.accountNumber());
        bankAccount.setIban(bankAccountDto.iban());
        bankAccount.setAccountName(bankAccountDto.accountName());
        bankAccount.setBank(bank);
        bankAccount.setLegalEntity(legalEntity);
        
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        BankAccountDto response = new BankAccountDto(
            savedBankAccount.getId(), 
            savedBankAccount.getAccountNumber(), 
            savedBankAccount.getIban(),
            savedBankAccount.getAccountName(), 
            bank.getId(),
            legalEntity.getId()
            );
        return new GenericResponse<>("Bank Account created successfully", response);
    }

    @Override
    public GenericResponse<BankAccountDto> update(BankAccountDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank account id is required");
        }

        if (dto.accountName() == null || dto.accountNumber() == null || dto.iban() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No given values to update");
        }

        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank account not found"));

        Optional<BankAccount> existingAccount = bankAccountRepository.findByAccountNumber(dto.accountNumber());
        if (existingAccount.isPresent() && !existingAccount.get().getId().equals(bankAccount.getId()) && existingAccount.get().getBank().getId().equals(bankAccount.getBank().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another account with same account number within same bank");
        }

        existingAccount = bankAccountRepository.findByIban(dto.iban());
        if (existingAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another account with same IBAN");
        }

        // Bank bank = bankRepository.findById(dto.BankId())
        //         .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank not found"));

        if (dto.accountNumber() != null) {
            bankAccount.setAccountNumber(dto.accountNumber());
        }
        if (dto.iban() != null) {
            bankAccount.setIban(dto.iban());
        }
        if (dto.accountName() != null) {
            bankAccount.setAccountName(dto.accountName());
        }
        
        // bankAccount.setBank(bank);

        BankAccount updatedBankAccount = bankAccountRepository.save(bankAccount);

        BankAccountDto response = new BankAccountDto(
                updatedBankAccount.getId(),
                updatedBankAccount.getAccountNumber(),
                updatedBankAccount.getIban(),
                updatedBankAccount.getAccountName(),
                updatedBankAccount.getBank().getId(),
                updatedBankAccount.getLegalEntity().getId()
        );
        return new GenericResponse<>("Bank account updated successfully", response);
    }

    @Override
    public GenericResponse<List<BankAccountDto>> findAll() {
        List<BankAccountDto> accounts = bankAccountRepository.findAll().stream()
                .map(acc -> new BankAccountDto(
                        acc.getId(),
                        acc.getAccountNumber(),
                        acc.getIban(),
                        acc.getAccountName(),
                        acc.getBank().getId(),
                        acc.getLegalEntity().getId()
                ))
                .toList();

        return new GenericResponse<>("Bank accounts retrieved successfully", accounts);
    }

    @Override
    public GenericResponse<BankAccountDto> findById(Long id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank account not found"));

        BankAccountDto response = new BankAccountDto(
                bankAccount.getId(),
                bankAccount.getAccountNumber(),
                bankAccount.getIban(),
                bankAccount.getAccountName(),
                bankAccount.getBank().getId(),
                bankAccount.getLegalEntity().getId()
        );

        return new GenericResponse<>("Bank account retrieved successfully", response);
    }

    @Override
    public GenericResponse<List<BankAccountDto>> findAllByBank(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank not found"));

        List<BankAccountDto> accounts = bankAccountRepository.findByBankId(bankId).stream()
                .map(acc -> new BankAccountDto(
                        acc.getId(),
                        acc.getAccountNumber(),
                        acc.getIban(),
                        acc.getAccountName(),
                        bank.getId(),
                        acc.getLegalEntity().getId()
                ))
                .toList();

        return new GenericResponse<>("Bank accounts for bank retrieved successfully", accounts);
    }

}
