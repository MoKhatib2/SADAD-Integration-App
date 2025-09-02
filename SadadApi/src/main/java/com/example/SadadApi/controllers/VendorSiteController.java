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
import com.example.SadadApi.dtos.VendorSiteDto;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.VendorSiteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendor-sites")
public class VendorSiteController {
    final private VendorSiteService vendorSiteService;

    public VendorSiteController(VendorSiteService vendorSiteService){
        this.vendorSiteService = vendorSiteService;
    }

     @PostMapping
    public ResponseEntity<?> create(@Valid VendorSiteDto dto) {
        return ResponseEntity.ok(vendorSiteService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid CodeNameDto dto) {
        return ResponseEntity.ok(vendorSiteService.update(id, dto));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(vendorSiteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorSiteService.findById(id));
    }

    @GetMapping("/{id}/vendors")
    public ResponseEntity<?> getByVendorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendorSiteService.findAllByVendor(id));
    }
}
