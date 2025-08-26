package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BankAccountDto(Long id, @NotBlank String accountNumber, @NotBlank String iban, @NotBlank String accountName, @NotNull Long BankId, @NotNull Long legalEntityId) {

}
