package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.Organization;
import com.example.SadadApi.repositories.OrganizationRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.OrganizationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService{

    final private OrganizationRepository organizationRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        organizationRepository.findByCode(dto.code())
        .ifPresent(b -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another organization with this code");
        });

        Organization costCenter = new Organization();
        costCenter.setCode(dto.code());
        costCenter.setName(dto.name());
        
        Organization saved = organizationRepository.save(costCenter);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("organization created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization id is required");
        }

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization not found"));

        // Ensure code uniqueness
        Optional<Organization> existing = organizationRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(organization.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another organization with this code");
        }

        if (organization.getCode() != null) {
            organization.setCode(dto.code());
        }
        if (organization.getName() != null) {
            organization.setName(dto.name());
        }

        Organization updated = organizationRepository.save(organization);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("Organization updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> organizations = organizationRepository.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getName()))
                .toList();

        return new GenericResponse<>("Organizations retrieved successfully", organizations);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization not found"));

        CodeNameResponse response = new CodeNameResponse(organization.getId(), organization.getCode(), organization.getName());
        return new GenericResponse<>("Organization retrieved successfully", response);
    }

}
