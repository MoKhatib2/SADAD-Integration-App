package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record CodeNameDto(@NotBlank String code, @NotBlank String name) {

}
