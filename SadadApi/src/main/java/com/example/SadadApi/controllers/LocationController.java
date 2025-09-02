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
import com.example.SadadApi.services.LocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/locations")
public class LocationController {
    final private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

        @PostMapping
    public ResponseEntity<?> create(@Valid CodeNameDto dto) {
        return ResponseEntity.ok(locationService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid CodeNameDto dto) {
        return ResponseEntity.ok(locationService.update(dto, id));
    }
    
    @GetMapping
    public ResponseEntity<GenericResponse<List<CodeNameResponse>>> getAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }
}
