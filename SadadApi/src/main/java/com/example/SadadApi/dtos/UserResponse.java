package com.example.SadadApi.dtos;

import com.example.SadadApi.models.User.Role;

public record UserResponse(String firstName, String lastName, String email, Role role) {

}
