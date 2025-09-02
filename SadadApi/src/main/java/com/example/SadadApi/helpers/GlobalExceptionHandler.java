package com.example.SadadApi.helpers;

import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

import com.example.SadadApi.responses.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<MessageResponse> handleAuthenticationException(Exception ex) {
        MessageResponse error = new MessageResponse("fail", "UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put("message",error.getField() + " " + error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<MessageResponse> handleResponseStatusException(ResponseStatusException ex) {
        MessageResponse error = new MessageResponse("fail", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<MessageResponse> handleJWTVerificationException(JWTVerificationException ex) {
        MessageResponse error = new MessageResponse("fail", "UNAUTHORIZED");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<MessageResponse> handleAnyException(NoResourceFoundException ex) {
        MessageResponse error = new MessageResponse("fail", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleAnyException(Exception ex) {
        MessageResponse error = new MessageResponse("fail", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
