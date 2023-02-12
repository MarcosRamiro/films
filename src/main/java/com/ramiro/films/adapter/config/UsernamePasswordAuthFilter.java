package com.ramiro.films.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramiro.films.adapter.dto.CredentialsRequest;
import com.ramiro.films.domain.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final UserAuthenticationProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("/api/login".equals(request.getServletPath())
                && HttpMethod.POST.matches(request.getMethod())) {

            CredentialsRequest credentials = MAPPER.readValue(request.getInputStream(), CredentialsRequest.class);

            try {

                SecurityContextHolder.getContext().setAuthentication(
                        provider.validateCredentials(credentials)
                );

            } catch (UserNotFoundException e) {
                SecurityContextHolder.clearContext();
                throw new RuntimeException(e);

            }
        }

        filterChain.doFilter(request, response);

    }

}