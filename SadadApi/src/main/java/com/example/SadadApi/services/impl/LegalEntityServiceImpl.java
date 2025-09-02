package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.LegalEntity;
import com.example.SadadApi.repositories.LegalEntityRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.LegalEntityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LegalEntityServiceImpl implements LegalEntityService {

    private final LegalEntityRepository legalEntityRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        LegalEntity entity = new LegalEntity();
        entity.setName(dto.name());
        entity.setCode(dto.code());

        LegalEntity saved = legalEntityRepository.save(entity);
        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("Legal Entity created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        LegalEntity entity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found"));

        Optional<LegalEntity> existing = legalEntityRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(entity.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another legal entity with this code");
        }
        
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.code() != null) entity.setCode(dto.code());

        LegalEntity updated = legalEntityRepository.save(entity);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("Legal Entity updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> list = legalEntityRepository.findAll()
                .stream().map(this::toDto)
                .toList();
        return new GenericResponse<>("Legal Entities retrieved successfully", list);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        LegalEntity entity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Legal Entity not found"));
        
        CodeNameResponse response = new CodeNameResponse(entity.getId(), entity.getCode(), entity.getName());
        return new GenericResponse<>("Legal Entity retrieved successfully", response);
    }

    private CodeNameResponse toDto(LegalEntity entity) {
        return new CodeNameResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        );
    }
}

