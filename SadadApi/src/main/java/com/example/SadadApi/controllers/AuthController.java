package com.example.SadadApi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SadadApi.dtos.EmailDto;
import com.example.SadadApi.dtos.ResetPasswordDto;
import com.example.SadadApi.dtos.SignInDto;
import com.example.SadadApi.dtos.SignUpDto;
import com.example.SadadApi.dtos.VerificationCodeDto;
import com.example.SadadApi.responses.AuthResponse;
import com.example.SadadApi.responses.MessageResponse;
import com.example.SadadApi.services.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {
    final private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

    @PostMapping("signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody @Valid SignInDto signInDto) {
        return ResponseEntity.ok(authService.signIn(signInDto));
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<MessageResponse> forgetPassword(@RequestBody @Valid EmailDto emailDto) {
       return ResponseEntity.ok(authService.forgetPassword(emailDto));
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<MessageResponse> verifyCode(@RequestBody @Valid VerificationCodeDto verificationCodeDto) {
       return ResponseEntity.ok(authService.verifyCode(verificationCodeDto));
    }
    
    @PutMapping("/resetPassword")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok(authService.resetPassword(resetPasswordDto));
    }
    
}
