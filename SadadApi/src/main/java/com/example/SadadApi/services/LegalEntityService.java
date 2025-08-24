package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.dtos.CodeNameResponse;
import com.example.SadadApi.dtos.GenericResponse;

public interface LegalEntityService {
    GenericResponse<CodeNameResponse> create(CodeNameDto codeNameDto);
    GenericResponse<CodeNameResponse> update(CodeNameResponse CodeNameResponse);
    GenericResponse<List<CodeNameResponse>> findAll();
    GenericResponse<CodeNameResponse> findById();
    //GenericResponse<List<CodeNameResponse>> findAllByOrganiztion();
}
