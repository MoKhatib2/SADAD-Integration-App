package com.example.SadadApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignInDto(
    @NotBlank(message = "email is required") @Email String email, 
    @NotBlank(message = "password is required")     
    String password) {

}
