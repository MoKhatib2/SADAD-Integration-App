package com.example.SadadApi.services.impl;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.InvoiceType;
import com.example.SadadApi.repositories.InvoiceTypeRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.InvoiceTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceTypeImpl implements InvoiceTypeService{

    final private InvoiceTypeRepository invoiceTypeRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        InvoiceType invoiceType = new InvoiceType();

        invoiceType.setName(dto.name());
        
        InvoiceType saved = invoiceTypeRepository.save(invoiceType);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getName(), saved.getName());
        return new GenericResponse<>("invoiceType created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invoiceType id is required");
        }

        InvoiceType invoiceType = invoiceTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invoiceType not found"));

        invoiceType.setName(dto.name());

        InvoiceType updated = invoiceTypeRepository.save(invoiceType);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getName(), updated.getName());
        return new GenericResponse<>("invoiceType updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> invoiceTypees = invoiceTypeRepository.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getName(), b.getName()))
                .toList();

        return new GenericResponse<>("invoiceTypes retrieved successfully", invoiceTypees);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        InvoiceType invoiceType = invoiceTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invoiceType not found"));

        CodeNameResponse response = new CodeNameResponse(invoiceType.getId(), invoiceType.getName(), invoiceType.getName());
        return new GenericResponse<>("invoiceType retrieved successfully", response);
    }


}
