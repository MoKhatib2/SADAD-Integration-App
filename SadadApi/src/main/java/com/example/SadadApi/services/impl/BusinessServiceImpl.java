package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.Business;
import com.example.SadadApi.repositories.BusinessRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.BusinessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService{
    final private BusinessRepository businessRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        businessRepository.findByCode(dto.code())
                .ifPresent(b -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another business with this code");
                });

        Business business = new Business();
        business.setCode(dto.code());
        business.setName(dto.name());
        Business saved = businessRepository.save(business);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("Business created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business id is required");
        }

        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business not found"));

        // Ensure code uniqueness
        Optional<Business> existing = businessRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(business.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another business with this code");
        }

        if (business.getCode() != null) {
            business.setCode(dto.code());
        }
        if (business.getName() != null) {
            business.setName(dto.name());
        }

        Business updated = businessRepository.save(business);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("Business updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> businesses = businessRepository.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getName()))
                .toList();

        return new GenericResponse<>("Businesses retrieved successfully", businesses);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business not found"));

        CodeNameResponse response = new CodeNameResponse(business.getId(), business.getCode(), business.getName());
        return new GenericResponse<>("Business retrieved successfully", response);
    }

}
