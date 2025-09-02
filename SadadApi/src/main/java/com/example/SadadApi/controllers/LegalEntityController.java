package com.example.SadadApi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.LegalEntityService;


@RestController
@RequestMapping("/legal-entities")
public class LegalEntityController {
    final private LegalEntityService legalEntityService;

    public LegalEntityController(LegalEntityService legalEntityService) {
        this.legalEntityService = legalEntityService;
    }

     @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(legalEntityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(legalEntityService.findById(id));
    }
}
