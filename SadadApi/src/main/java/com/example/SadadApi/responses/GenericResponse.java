package com.example.SadadApi.responses;

public record GenericResponse<T>(String message, T data) {

}
