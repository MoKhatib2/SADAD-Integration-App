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
import com.example.SadadApi.services.InvoiceTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/invoice-types")
public class InvoiceTypeController {
    final private InvoiceTypeService invoiceTypeService;

    public InvoiceTypeController(InvoiceTypeService invoiceTypeService) {
        this.invoiceTypeService = invoiceTypeService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid CodeNameDto dto) {
        return ResponseEntity.ok(invoiceTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid CodeNameDto dto) {
        return ResponseEntity.ok(invoiceTypeService.update(dto, id));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(invoiceTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceTypeService.findById(id));
    }
}
