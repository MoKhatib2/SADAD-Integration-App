package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.dtos.VendorDto;
import com.example.SadadApi.models.Biller;
import com.example.SadadApi.models.Vendor;
import com.example.SadadApi.repositories.BillerRepository;
import com.example.SadadApi.repositories.VendorRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.VendorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService{
    final private VendorRepository vendorRepository;
    final private BillerRepository billerRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(VendorDto vendorDto) {
        // Check unique code
        Optional<Vendor> existing = vendorRepository.findByCode(vendorDto.code());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another vendor with this code");
        }

        // Ensure biller exists
        Biller biller = billerRepository.findById(vendorDto.billerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Biller not found"));

        Vendor vendor = new Vendor();
        vendor.setCode(vendorDto.code());
        vendor.setName(vendorDto.name());
        vendor.setBiller(biller);

        Vendor saved = vendorRepository.save(vendor);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("Vendor created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(Long id, CodeNameDto dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor id is required");
        }

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found"));

        // Ensure code uniqueness
        Optional<Vendor> existing = vendorRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(vendor.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another vendor with this code");
        }

        if (vendor.getCode() != null) {
            vendor.setCode(dto.code());
        }
        if (vendor.getName() != null) {
            vendor.setName(dto.name());
        }

        Vendor updated = vendorRepository.save(vendor);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("Vendor updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> vendors = vendorRepository.findAll().stream()
                .map(v -> new CodeNameResponse(v.getId(), v.getCode(), v.getName()))
                .toList();

        return new GenericResponse<>("Vendors retrieved successfully", vendors);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found"));

        CodeNameResponse response = new CodeNameResponse(vendor.getId(), vendor.getCode(), vendor.getName());
        return new GenericResponse<>("Vendor retrieved successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAllByBiller(Long billerId) {
        List<CodeNameResponse> vendors = vendorRepository.findAllByBillerId(billerId).stream()
                .map(v -> new CodeNameResponse(v.getId(), v.getCode(), v.getName()))
                .toList();

        return new GenericResponse<>("Vendors retrieved successfully by biller", vendors);
    }
}

