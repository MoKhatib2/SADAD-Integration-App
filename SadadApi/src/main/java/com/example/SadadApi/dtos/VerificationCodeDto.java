package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record VerificationCodeDto(@NotBlank String email, @NotBlank String code) {

}
