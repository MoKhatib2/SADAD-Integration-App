package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;

public interface CrudService {
    GenericResponse<CodeNameResponse> create(CodeNameDto codeNameDto);
    GenericResponse<CodeNameResponse> update(CodeNameDto CodeNameDto, Long id);
    GenericResponse<List<CodeNameResponse>> findAll();
    GenericResponse<CodeNameResponse> findById(Long id);
    //MessageResponse deleteById(Long id);
}
