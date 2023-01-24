package com.ramiro.films.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                log.info("has Bearer Token");
                try {

                    SecurityContextHolder.getContext().setAuthentication(
                            provider.validateToken(authElements[1]));
                    log.info("Bearer Token OK");
                } catch (RuntimeException e) {
                    log.error("Error on validateToken", e);
                    SecurityContextHolder.clearContext();
                    throw e;
                }

            } else {
                log.info(String.format("Not found Bearer Token. Method: %s Path: %s.",
                        request.getMethod(),
                        request.getServletPath()));
            }

        } else {
            log.info(String.format("Not found Authentication Header. Method: %s Path: %s.",
                    request.getMethod(),
                    request.getServletPath()));
        }

        filterChain.doFilter(request, response);

    }

}
