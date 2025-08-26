package com.example.SadadApi.services.impl;

import java.time.Instant;
import java.util.Random;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.config.TokenProvider;
import com.example.SadadApi.dtos.EmailDto;
import com.example.SadadApi.dtos.ResetPasswordDto;
import com.example.SadadApi.dtos.SignInDto;
import com.example.SadadApi.dtos.SignUpDto;
import com.example.SadadApi.dtos.VerificationCodeDto;
import com.example.SadadApi.models.PasswordResetToken;
import com.example.SadadApi.models.User;
import com.example.SadadApi.models.User.Role;
import com.example.SadadApi.repositories.PasswordResetTokenRepository;
import com.example.SadadApi.repositories.UserRepository;
import com.example.SadadApi.responses.AuthResponse;
import com.example.SadadApi.responses.MessageResponse;
import com.example.SadadApi.responses.UserResponse;
import com.example.SadadApi.services.AuthService;
import com.example.SadadApi.services.EmailService;

@Service
public class AuthServiceImpl implements AuthService{
    final private UserRepository userRepository;
    final private TokenProvider tokenProvider;
    final private AuthenticationManager authenticationManager;
    final private PasswordResetTokenRepository passwordResetTokenRepository;
    final private EmailService emailService;

    public AuthServiceImpl(
        UserRepository userRepository, 
        TokenProvider tokenProvider, 
        AuthenticationManager authenticationManager,
        PasswordResetTokenRepository passwordResetTokenRepository,
        EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public AuthResponse signUp(SignUpDto signUpDto) throws ResponseStatusException {
        if(userRepository.findByEmail(signUpDto.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(signUpDto.password());
        User user = User.builder()
                        .firstName(signUpDto.firstName())
                        .lastName(signUpDto.lastName())
                        .email(signUpDto.email())
                        .password(encryptedPassword)
                        .role(Role.valueOf(signUpDto.role().toUpperCase()))
                        .approved(true)
                        .build();
        User savedUser = userRepository.save(user);
        String token = tokenProvider.generateAccessToken(savedUser);
        return new AuthResponse(
            "Sign up Successful", 
            new UserResponse(savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(), savedUser.getRole()),
            token
            );   
    }

    @Override
    public AuthResponse signIn(SignInDto signInDto) throws ResponseStatusException {
        User user = userRepository.findByEmail(signInDto.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Email or password is incorrect"));
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.email(), signInDto.password())
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Email or password is incorrect");
        }
        String token = tokenProvider.generateAccessToken(user);
        return new AuthResponse(
            "Sign up Successful", 
            new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole()),
            token
        );   
    }

    @Override
    public MessageResponse forgetPassword(EmailDto emailDto) throws ResponseStatusException {
        User user = userRepository.findByEmail(emailDto.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Email or password is incorrect"));

        String code = generateCode();
        Instant expiry = Instant.now().plusSeconds(60 * 10);
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, code, expiry, false);
        passwordResetTokenRepository.save(passwordResetToken);
        emailService.SendSimpleEmail(emailDto.email(), "Password reset verification code", code);
        return new MessageResponse("Success", "A verification code was sent to your email");
    }

    @Override
    public MessageResponse verifyCode(VerificationCodeDto verificationCodeDto) {
        User user = userRepository.findByEmail(verificationCodeDto.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED , "There is no account with this email"));
    
        Set<PasswordResetToken> resetTokens = passwordResetTokenRepository.findByUser(user);
        boolean found = false;
        boolean codeExpired = false;
        for (PasswordResetToken resetToken : resetTokens) {
            if (resetToken.getToken().equals(verificationCodeDto.code()) && !resetToken.isVerified()) {
                if (resetToken.getExpiryDate().isAfter(Instant.now()) ) {
                    resetToken.setVerified(true);
                    passwordResetTokenRepository.save(resetToken);
                } else {
                    codeExpired = true;
                } 
                found = true;   
            }
        }

        if (codeExpired) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification code is expired");
        }
        if (!found) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification code is incorrect");
        }
        return new MessageResponse("Success", "code verified");
    }

    @Override
    public MessageResponse resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = userRepository.findByEmail(resetPasswordDto.email())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED , "There is no account with this email"));
            
        Set<PasswordResetToken> resetTokens = passwordResetTokenRepository.findByUser(user);
        boolean found = false;
        for (PasswordResetToken resetToken : resetTokens) {
            if (resetToken.isVerified() && resetToken.getExpiryDate().isAfter(Instant.now())) {
                found = true;
            }
        }
        if (!found) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Verified");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(resetPasswordDto.password());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return new MessageResponse("Success", "Password reset");
    }

    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
