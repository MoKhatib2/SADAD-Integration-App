package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.PasswordResetToken;
import com.example.SadadApi.models.User;

import java.util.Set;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    public Set<PasswordResetToken> findByUser(User user);
}
