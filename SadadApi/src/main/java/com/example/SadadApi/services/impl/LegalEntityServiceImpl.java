package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.LegalEntityDto;
import com.example.SadadApi.models.LegalEntity;
import com.example.SadadApi.repositories.LegalEntityRepository;
import com.example.SadadApi.repositories.OrganizationRepository;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.LegalEntityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LegalEntityServiceImpl implements LegalEntityService {

    private final LegalEntityRepository legalEntityRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    public GenericResponse<LegalEntityDto> create(LegalEntityDto dto) {
        var org = organizationRepository.findById(dto.organizationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization not found"));

        LegalEntity entity = new LegalEntity();
        entity.setName(dto.name());
        entity.setCode(dto.code());
        entity.setOrganization(org);

        LegalEntity saved = legalEntityRepository.save(entity);
        LegalEntityDto response = new LegalEntityDto(saved.getId(), saved.getCode(), saved.getName(), saved.getOrganization().getId());
        return new GenericResponse<>("Legal Entity created successfully", response);
    }

    @Override
    public GenericResponse<LegalEntityDto> update(LegalEntityDto dto, Long id) {
        LegalEntity entity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found"));

        Optional<LegalEntity> existing = legalEntityRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(entity.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another expenseAccount with this code");
        }
        
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.code() != null) entity.setCode(dto.code());

        if (dto.organizationId() != null) {
            var org = organizationRepository.findById(dto.organizationId())
                    .orElseThrow(() -> new RuntimeException("Organization not found"));
            entity.setOrganization(org);
        }

        LegalEntity updated = legalEntityRepository.save(entity);

        LegalEntityDto response = new LegalEntityDto(updated.getId(), updated.getCode(), updated.getName(), updated.getOrganization().getId());
        return new GenericResponse<>("Legal Entity updated successfully", response);
    }

    @Override
    public GenericResponse<List<LegalEntityDto>> findAll() {
        List<LegalEntityDto> list = legalEntityRepository.findAll()
                .stream().map(this::toDto)
                .toList();
        return new GenericResponse<>("Legal Entities retrieved successfully", list);
    }

    @Override
    public GenericResponse<LegalEntityDto> findById(Long id) {
        LegalEntity entity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found"));
        
        LegalEntityDto response = new LegalEntityDto(entity.getId(), entity.getCode(), entity.getName(), entity.getOrganization().getId());
        return new GenericResponse<>("Legal Entity retrieved successfully", response);
    }

    @Override
    public GenericResponse<List<LegalEntityDto>> findAllByOrganiztion(IdDto idDto) {
        var org = organizationRepository.findById(idDto.id())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        List<LegalEntityDto> list = legalEntityRepository.findByOrganization(org)
                .stream().map(this::toDto)
                .toList();

        return new GenericResponse<>("Legal Entity retrieved successfully", list);
    }

    private LegalEntityDto toDto(LegalEntity entity) {
        return new LegalEntityDto(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getOrganization().getId()
        );
    }
}

