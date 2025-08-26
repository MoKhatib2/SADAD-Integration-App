package com.example.SadadApi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.SadadApi.services.impl.DetailsService;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final DetailsService detailsService;

    public SecurityFilter(TokenProvider tokenProvider, DetailsService detailsService) {
        this.tokenProvider = tokenProvider;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "").trim();

            try {
                Long userId = Long.valueOf(tokenProvider.validateToken(token)); 
                UserDetails userDetails = detailsService.loadUserById(userId);
                System.out.println(userDetails);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; 
            }
        }

        filterChain.doFilter(request, response);
    }
}
