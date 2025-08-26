package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record LegalEntityDto(Long id, @NotBlank String code, @NotBlank String name, Long organizationId) {

}
