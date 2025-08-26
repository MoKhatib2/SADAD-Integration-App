package com.example.SadadApi.services;

import com.example.SadadApi.dtos.EmailDto;
import com.example.SadadApi.dtos.ResetPasswordDto;
import com.example.SadadApi.dtos.SignInDto;
import com.example.SadadApi.dtos.SignUpDto;
import com.example.SadadApi.dtos.VerificationCodeDto;
import com.example.SadadApi.responses.AuthResponse;
import com.example.SadadApi.responses.MessageResponse;

public interface AuthService {
    AuthResponse signUp(SignUpDto signUpDto);
    AuthResponse signIn(SignInDto signInDto);
    MessageResponse forgetPassword(EmailDto emailDto);
    MessageResponse verifyCode(VerificationCodeDto verificationCodeDto);
    MessageResponse resetPassword(ResetPasswordDto resetPasswordDto);
}
