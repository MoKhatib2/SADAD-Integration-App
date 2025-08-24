package com.example.SadadApi.dtos;

public record GenericResponse<T>(String message, T data) {

}
