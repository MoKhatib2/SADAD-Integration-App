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
import com.example.SadadApi.dtos.VendorDto;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.VendorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    final private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid VendorDto dto) {
        return ResponseEntity.ok(vendorService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid CodeNameDto dto) {
        return ResponseEntity.ok(vendorService.update(id, dto));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(vendorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.findById(id));
    }

    @GetMapping("/{id}/billers")
    public ResponseEntity<?> getByBillerId(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.findAllByBiller(id));
    }
}
