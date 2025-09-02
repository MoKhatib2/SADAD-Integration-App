package com.example.SadadApi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.SadadRecordDto;
import com.example.SadadApi.services.SadadRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sadad-records")
public class SadadRecordController {

    final private SadadRecordService sadadService;

    public SadadRecordController(SadadRecordService sadadService) {
        this.sadadService = sadadService;
    }

    @PreAuthorize("hasRole('ENTRY')")
    @PostMapping
    public ResponseEntity<?> createRecord(@RequestBody @Valid SadadRecordDto dto) {
       return ResponseEntity.ok(sadadService.createRecord(dto));
    }

    @PreAuthorize("hasAnyRole('ENTRY','APPROVE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable Long id, @RequestBody @Valid SadadRecordDto dto) {
        return ResponseEntity.ok(sadadService.updateRecord(dto, id));
    }

    @PreAuthorize("hasRole('ENTRY')")
    @PostMapping("/duplicate")
    public ResponseEntity<?> duplicateRecord(@RequestBody @Valid IdDto dto) {
        return ResponseEntity.ok(sadadService.duplicateRecord(dto));
    }

    @PreAuthorize("hasRole('APPROVE')")
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confrimRecord(@PathVariable Long id) {
        return ResponseEntity.ok(sadadService.confirmRecord(id));
    }

    @PreAuthorize("hasRole('APPROVE')")
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelRecord(@PathVariable Long id) {
        return ResponseEntity.ok(sadadService.cancelRecord(id));
    }

    @PreAuthorize("hasRole('RELEASE')")
    @PutMapping("/release/{id}")
    public ResponseEntity<?> releaseRecord(@PathVariable Long id) {
        return ResponseEntity.ok(sadadService.releaseRecord(id));
    }

    @PreAuthorize("hasRole('RELEASE')")
    @PutMapping("/retry-invoice/{id}")
    public ResponseEntity<?> retryInvoiceRecord(@PathVariable Long id) {
        return ResponseEntity.ok(sadadService.retryInvoiceRecord(id));
    }
    
    @GetMapping
    public ResponseEntity<?> getAllRecords() {
        return ResponseEntity.ok(sadadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sadadService.findById(id));
    }
    
    
}
