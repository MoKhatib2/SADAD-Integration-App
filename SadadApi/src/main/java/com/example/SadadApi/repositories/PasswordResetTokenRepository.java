package com.example.SadadApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.PasswordResetToken;
import com.example.SadadApi.models.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RepositoryRestResource(exported = false)
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    public Set<PasswordResetToken> findByUser(User user);
}
