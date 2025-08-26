package com.example.SadadApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record SadadRecordStatusDto(@NotBlank String status) {

}
