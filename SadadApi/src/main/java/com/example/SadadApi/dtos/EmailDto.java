package com.example.SadadApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(@NotBlank(message = "Email is required") @Email String email) {

}
