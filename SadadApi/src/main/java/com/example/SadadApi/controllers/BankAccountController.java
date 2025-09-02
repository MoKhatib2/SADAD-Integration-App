package com.example.SadadApi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SadadApi.dtos.BankAccountDto;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.BankAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {
    final private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<BankAccountDto>> create(@Valid BankAccountDto dto) {
        return ResponseEntity.ok(bankAccountService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, BankAccountDto dto) {
        return ResponseEntity.ok(bankAccountService.update(dto, id));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<BankAccountDto>>> getAll() {
        return ResponseEntity.ok(bankAccountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bankAccountService.findById(id));
    }

    @GetMapping("/{id}/banks")
    public ResponseEntity<?> getAllByBankId(@PathVariable Long id) {
        return ResponseEntity.ok(bankAccountService.findAllByBank(id));
    }

    @GetMapping("/getBybankAndOrganization")
    public ResponseEntity<?> getAllByBankAndOrganization(@RequestParam Long bankId, @RequestParam Long organizationId ) {
        return ResponseEntity.ok(bankAccountService.findAllByBankandOrganization(bankId, organizationId));
    }

}
