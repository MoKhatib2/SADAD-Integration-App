package com.example.SadadApi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.BankService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/banks")
public class BankController {

    final private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid CodeNameDto dto) {
        return ResponseEntity.ok(bankService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, CodeNameDto dto) {
        return ResponseEntity.ok(bankService.update(dto, id));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(bankService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.findById(id));
    }

    @GetMapping("/{id}/organizations")
    public ResponseEntity<?> getByOrganization(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.findAllByOrganiztion(id));
    }
    
}
