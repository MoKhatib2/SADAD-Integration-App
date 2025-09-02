package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record BankAccountDto(Long id, @NotBlank String accountNumber, @NotBlank String iban, @NotBlank String accountName, Long bankId, Long organizationId) {

}
