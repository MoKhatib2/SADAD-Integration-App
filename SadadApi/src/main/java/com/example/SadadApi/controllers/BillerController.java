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
import com.example.SadadApi.services.BillerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/billers")
public class BillerController {
    final private BillerService billerService;

    public BillerController(BillerService billerService) {
        this.billerService = billerService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid CodeNameDto dto) {
        return ResponseEntity.ok(billerService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid CodeNameDto dto) {
        return ResponseEntity.ok(billerService.update(dto, id));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(billerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(billerService.findById(id));
    }
}
