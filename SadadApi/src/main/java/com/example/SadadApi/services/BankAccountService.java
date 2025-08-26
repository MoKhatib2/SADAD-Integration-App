package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.BankAccountDto;
import com.example.SadadApi.responses.GenericResponse;

public interface BankAccountService {
    GenericResponse<BankAccountDto> create(BankAccountDto dto);
    GenericResponse<BankAccountDto> update(BankAccountDto dto, Long id);
    GenericResponse<List<BankAccountDto>> findAll();
    GenericResponse<BankAccountDto> findById(Long id);
    GenericResponse<List<BankAccountDto>> findAllByBank(Long bankId);
}
