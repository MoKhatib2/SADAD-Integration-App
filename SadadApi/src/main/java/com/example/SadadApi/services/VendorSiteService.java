package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.VendorSiteDto;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;

public interface VendorSiteService {
    GenericResponse<CodeNameResponse> create(VendorSiteDto vendorSiteDto);
    GenericResponse<CodeNameResponse> update(VendorSiteDto VendorSiteDto);
    GenericResponse<List<CodeNameResponse>> findAll();
    GenericResponse<CodeNameResponse> findById(Long id);
    //MessageResponse deleteById(Long id);
    GenericResponse<List<CodeNameResponse>> findAllByVendor(IdDto idDto);
}
