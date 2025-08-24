package com.example.SadadApi.services.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.config.TokenProvider;
import com.example.SadadApi.dtos.AuthResponse;
import com.example.SadadApi.dtos.EmailDto;
import com.example.SadadApi.dtos.MessageResponse;
import com.example.SadadApi.dtos.SignInDto;
import com.example.SadadApi.dtos.SignUpDto;
import com.example.SadadApi.dtos.UserResponse;
import com.example.SadadApi.dtos.VerificationCodeDto;
import com.example.SadadApi.models.PasswordResetToken;
import com.example.SadadApi.models.User;
import com.example.SadadApi.models.User.Role;
import com.example.SadadApi.repositories.PasswordResetTokenRepository;
import com.example.SadadApi.repositories.UserRepository;
import com.example.SadadApi.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
    final private UserRepository userRepository;
    final private TokenProvider tokenProvider;
    final private AuthenticationManager authenticationManager;
    final private PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthServiceImpl(
        UserRepository userRepository, 
        TokenProvider tokenProvider, 
        AuthenticationManager authenticationManager,
        PasswordResetTokenRepository passwordResetTokenRepository
    ) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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
        Optional<User> user = userRepository.findByEmail(signInDto.email());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Email or password is incorrect");
        }
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDto.email(), signInDto.password())
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Email or password is incorrect");
        }
        String token = tokenProvider.generateAccessToken(user.get());
        return new AuthResponse(
            "Sign up Successful", 
            new UserResponse(user.get().getFirstName(), user.get().getLastName(), user.get().getEmail(), user.get().getRole()),
            token
        );   
    }

    @Override
    public MessageResponse forgetPassword(EmailDto emailDto) throws ResponseStatusException {
        Optional<User> user = userRepository.findByEmail(emailDto.email());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "There is no account with this email");
        }
        String code = generateCode();
        Instant expiry = Instant.now().plusSeconds(60 * 10);
        PasswordResetToken passwordResetToken = new PasswordResetToken(user.get(), code, expiry, false);
        passwordResetTokenRepository.save(passwordResetToken);

        //send email
        return new MessageResponse("Success", "A verification code was sent to your email");
    }

    @Override
    public MessageResponse verifyCode(VerificationCodeDto verificationCodeDto) {
        Optional<User> user = userRepository.findByEmail(verificationCodeDto.email());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "There is no account with this email");
        }
        //check code
        return new MessageResponse("Success", "code verified");
    }

    @Override
    public AuthResponse resetPassword(EmailDto emailDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }

    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
