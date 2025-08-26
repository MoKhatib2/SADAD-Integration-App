package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.Bank;
import com.example.SadadApi.repositories.BankRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.BankService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService{
    final private BankRepository bankRepository;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto codeNameDto) {
        Optional<Bank> existingBank = bankRepository.findByCode(codeNameDto.code());
        if (existingBank.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another bank using this code");
        }
        Bank bank = new Bank();
        bank.setCode(codeNameDto.code());
        bank.setName(codeNameDto.name());
        Bank savedBank = bankRepository.save(bank);

        CodeNameResponse response = new CodeNameResponse(savedBank.getId(), savedBank.getCode(), savedBank.getCode());
        return new GenericResponse<>("Bank created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank id is required");
        }

        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank not found"));

        Optional<Bank> existingBank = bankRepository.findByCode(dto.code());
        if (existingBank.isPresent() && !existingBank.get().getId().equals(bank.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another bank using this code");
        }

        if (dto.code() != null) {
            bank.setCode(dto.code());
        }
        if (dto.name() != null) {
            bank.setName(dto.name());
        }

        Bank updatedBank = bankRepository.save(bank);

        CodeNameResponse response = new CodeNameResponse(updatedBank.getId(), updatedBank.getCode(), updatedBank.getName());
        return new GenericResponse<>("Bank updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> banks = bankRepository.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getName()))
                .toList();

        return new GenericResponse<>("Banks retrieved successfully", banks);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank not found"));

        CodeNameResponse response = new CodeNameResponse(bank.getId(), bank.getCode(), bank.getName());
        return new GenericResponse<>("Bank retrieved successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAllByOrganiztion(Long organizationId) {
        List<CodeNameResponse> banks = bankRepository.findByOrganizationId(organizationId).stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getName()))
                .toList();

        return new GenericResponse<>("Banks retrieved successfully", banks);
    }
}


