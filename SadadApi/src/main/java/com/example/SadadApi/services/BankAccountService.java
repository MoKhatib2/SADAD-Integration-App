package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.BankAccountDto;
import com.example.SadadApi.dtos.GenericResponse;

public interface BankAccountService {
    GenericResponse<BankAccountDto> create(BankAccountDto codeNameDto);
    GenericResponse<BankAccountDto> update(BankAccountDto CodeNameResponse);
    GenericResponse<List<BankAccountDto>> findAll();
    GenericResponse<BankAccountDto> findById();
    GenericResponse<List<BankAccountDto>> findAllByBank();
}
