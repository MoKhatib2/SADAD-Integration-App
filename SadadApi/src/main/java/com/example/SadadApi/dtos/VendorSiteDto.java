package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record VendorSiteDto(@NotBlank String code, @NotBlank String name, Long vendorId) {

}
