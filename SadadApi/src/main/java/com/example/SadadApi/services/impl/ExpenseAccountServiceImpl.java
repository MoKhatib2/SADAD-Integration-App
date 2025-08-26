package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.ExpenseAccount;
import com.example.SadadApi.repositories.ExpenseAccountRepositroy;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.ExpenseAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseAccountServiceImpl implements ExpenseAccountService{

    final private ExpenseAccountRepositroy expenseAccountRepositroy;

    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        // Ensure unique code
        expenseAccountRepositroy.findByCode(dto.code())
                .ifPresent(b -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another business with this code");
                });

        ExpenseAccount expenseAccount = new ExpenseAccount();
        if (expenseAccount.getCode() != null) {
            expenseAccount.setCode(dto.code());
        }
        if (expenseAccount.getName() != null) {
            expenseAccount.setName(dto.name());
        }

        ExpenseAccount saved = expenseAccountRepositroy.save(expenseAccount);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("expenseAccount created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "expenseAccount id is required");
        }

        ExpenseAccount expenseAccount = expenseAccountRepositroy.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "expenseAccount not found"));

        // Ensure code uniqueness
        Optional<ExpenseAccount> existing = expenseAccountRepositroy.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(expenseAccount.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another expenseAccount with this code");
        }

        expenseAccount.setCode(dto.code());
        expenseAccount.setName(dto.name());

        ExpenseAccount updated = expenseAccountRepositroy.save(expenseAccount);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("expenseAccount updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> expenseAccounts = expenseAccountRepositroy.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getName()))
                .toList();

        return new GenericResponse<>("expenseAccountes retrieved successfully", expenseAccounts);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        ExpenseAccount expenseAccount = expenseAccountRepositroy.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "expenseAccount not found"));

        CodeNameResponse response = new CodeNameResponse(expenseAccount.getId(), expenseAccount.getCode(), expenseAccount.getName());
        return new GenericResponse<>("expenseAccount retrieved successfully", response);
    }

}
