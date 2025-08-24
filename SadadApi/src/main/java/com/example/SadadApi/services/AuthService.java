package com.example.SadadApi.services;

import com.example.SadadApi.dtos.AuthResponse;
import com.example.SadadApi.dtos.EmailDto;
import com.example.SadadApi.dtos.MessageResponse;
import com.example.SadadApi.dtos.SignInDto;
import com.example.SadadApi.dtos.SignUpDto;
import com.example.SadadApi.dtos.VerificationCodeDto;

public interface AuthService {
    AuthResponse signUp(SignUpDto signUpDto);
    AuthResponse signIn(SignInDto signInDto);
    MessageResponse forgetPassword(EmailDto emailDto);
    MessageResponse verifyCode(VerificationCodeDto verificationCodeDto);
    AuthResponse resetPassword(EmailDto emailDto);
}
