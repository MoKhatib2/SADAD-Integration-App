package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.LegalEntityDto;
import com.example.SadadApi.responses.GenericResponse;

public interface LegalEntityService {
    GenericResponse<LegalEntityDto> create(LegalEntityDto dto);
    GenericResponse<LegalEntityDto> update(LegalEntityDto dto, Long id);
    GenericResponse<List<LegalEntityDto>> findAll();
    GenericResponse<LegalEntityDto> findById(Long id);
    GenericResponse<List<LegalEntityDto>> findAllByOrganiztion(IdDto idDto);
}
