package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.VendorDto;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;

public interface VendorService {
    GenericResponse<CodeNameResponse> create(VendorDto vendorDto);
    GenericResponse<CodeNameResponse> update(CodeNameResponse CodeNameResponse);
    GenericResponse<List<CodeNameResponse>> findAll();
    GenericResponse<CodeNameResponse> findById(Long id);
    //MessageResponse deleteById(Long id);
    GenericResponse<List<CodeNameResponse>> findAllByBiller(Long billerId);
}
