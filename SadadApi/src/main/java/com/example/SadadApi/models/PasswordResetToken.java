package com.example.SadadApi.models;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

    private Instant expiryDate;

    private boolean verified;

    public PasswordResetToken(User user, String token, Instant expiryDate, boolean verified) {
        this.user = user;
        this.token = token;
        this.expiryDate = expiryDate;
        this.verified = verified;
    }
}