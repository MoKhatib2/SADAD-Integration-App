package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

}
