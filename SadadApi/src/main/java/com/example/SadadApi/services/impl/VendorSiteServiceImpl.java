package com.example.SadadApi.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.VendorSiteDto;
import com.example.SadadApi.models.Vendor;
import com.example.SadadApi.models.VendorSite;
import com.example.SadadApi.repositories.VendorRepository;
import com.example.SadadApi.repositories.VendorSiteRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.VendorSiteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VendorSiteServiceImpl implements VendorSiteService {

    private final VendorSiteRepository vendorSiteRepository;
    private final VendorRepository vendorRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(VendorSiteDto dto) {
        vendorSiteRepository.findByCode(dto.code())
                .ifPresent(vs -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another VendorSite with this code");
                });

        if (dto.vendorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor is required");
        }

        Vendor vendor = vendorRepository.findById(dto.vendorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor not found"));

        VendorSite vendorSite = new VendorSite();
        vendorSite.setCode(dto.code());
        vendorSite.setName(dto.name());
        vendorSite.setVendor(vendor);

        VendorSite saved = vendorSiteRepository.save(vendorSite);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("VendorSite created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(VendorSiteDto dto) {
        if (dto.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "VendorSite id is required");
        }

        VendorSite vendorSite = vendorSiteRepository.findById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "VendorSite not found"));

        vendorSiteRepository.findByCode(dto.code()).ifPresent(existing -> {
            if (!existing.getId().equals(vendorSite.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another VendorSite with this code");
            }
        });

        if (dto.code() != null) {
            vendorSite.setCode(dto.code());
        }
        if (dto.name() != null) {
            vendorSite.setName(dto.name());
        }
    
        VendorSite updated = vendorSiteRepository.save(vendorSite);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("VendorSite updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> list = vendorSiteRepository.findAll().stream()
                .map(vs -> new CodeNameResponse(vs.getId(), vs.getCode(), vs.getName()))
                .toList();

        return new GenericResponse<>("VendorSite list retrieved successfully", list);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        VendorSite entity = vendorSiteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "VendorSite not found"));

        CodeNameResponse response = new CodeNameResponse(entity.getId(), entity.getCode(), entity.getName());
        return new GenericResponse<>("VendorSite retrieved successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAllByVendor(IdDto idDto) {
        Long vendorId = idDto.id();
        List<CodeNameResponse> list = vendorSiteRepository.findAllByVendorId(vendorId).stream()
                .map(vs -> new CodeNameResponse(vs.getId(), vs.getCode(), vs.getName()))
                .toList();

        return new GenericResponse<>("VendorSite list for vendor retrieved successfully", list);
    }


}

