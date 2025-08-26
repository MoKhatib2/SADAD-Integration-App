package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.models.CostCenter;
import com.example.SadadApi.repositories.CostCenterRepository;
import com.example.SadadApi.repositories.SadadCostCenterRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.responses.SadadCostCenterResponse;
import com.example.SadadApi.services.CostCenterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CostCenterServiceImpl implements CostCenterService{
    final private CostCenterRepository costCenterRepository;
    final private SadadCostCenterRepository sadadCostCenterRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto codeNameDto) {
        costCenterRepository.findByCode(codeNameDto.code())
        .ifPresent(b -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another cost center with this code");
        });

        CostCenter costCenter = new CostCenter();
        costCenter.setCode(codeNameDto.code());
        costCenter.setDescription(codeNameDto.name());
        
        CostCenter saved = costCenterRepository.save(costCenter);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getDescription());
        return new GenericResponse<>("costCenter created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "costCenter id is required");
        }

        CostCenter costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "costCenter not found"));

        // Ensure code uniqueness
        Optional<CostCenter> existing = costCenterRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(costCenter.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another costCenter with this code");
        }

        if (costCenter.getCode() != null) {
            costCenter.setCode(dto.code());
        }
        if (costCenter.getDescription() != null) {
            costCenter.setDescription(dto.name());
        }

        CostCenter updated = costCenterRepository.save(costCenter);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getDescription());
        return new GenericResponse<>("costCenter updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> costCenters = costCenterRepository.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getDescription()))
                .toList();

        return new GenericResponse<>("Cost Centers retrieved successfully", costCenters);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        CostCenter costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cost center not found"));

        CodeNameResponse response = new CodeNameResponse(costCenter.getId(), costCenter.getCode(), costCenter.getDescription());
        return new GenericResponse<>("Cost center retrieved successfully", response);
    }

    @Override
    public GenericResponse<List<SadadCostCenterResponse>> findAllBySadadRecord(IdDto iDto) {
        // Long sadadRecordId = iDto.id();
        // List<SadadCostCenter> sadadCostCenters = sadadCostCenterRepository.findAllBySadadRecordId(sadadRecordId).stream()
        //     .map(s -> new Sad)
        return null;
    }

}
