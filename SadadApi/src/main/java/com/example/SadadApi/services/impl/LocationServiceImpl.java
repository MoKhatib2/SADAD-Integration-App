package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.Location;
import com.example.SadadApi.repositories.LocationRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.LocationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        locationRepository.findByCode(dto.code())
                .ifPresent(l -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another location with this code");
                });

        Location location = new Location();
        location.setCode(dto.code());
        location.setName(dto.name());

        Location saved = locationRepository.save(location);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("Location created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location id is required");
        }

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        Optional<Location> existing = locationRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(location.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another location with this code");
        }

        if (location.getCode() != null) {
            location.setCode(dto.code());
        }
        if (location.getName() != null) {
            location.setName(dto.name());
        }

        Location updated = locationRepository.save(location);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("Location updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> locations = locationRepository.findAll().stream()
                .map(l -> new CodeNameResponse(l.getId(), l.getCode(), l.getName()))
                .toList();

        return new GenericResponse<>("Locations retrieved successfully", locations);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found"));

        CodeNameResponse response = new CodeNameResponse(location.getId(), location.getCode(), location.getName());
        return new GenericResponse<>("Location retrieved successfully", response);
    }


}
