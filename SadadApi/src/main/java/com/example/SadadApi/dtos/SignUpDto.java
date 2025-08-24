package com.example.SadadApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpDto(
    @NotBlank(message = "firstName is required") String firstName, 
    @NotBlank(message = "lastName is required") String lastName, 
    @NotBlank(message = "email is required") @Email String email, 
    @NotBlank(message = "password is required")     
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}$",
        message = "Password must be at least 8 characters, include at least one digit, one lowercase letter, one uppercase letter, and one special character."
    ) 
    String password, 
    @NotBlank(message = "role is required") String role) {

}
