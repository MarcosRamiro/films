package com.ramiro.films.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

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
					log.info("validated Bearer Token");
				} catch (RuntimeException e) {
					log.error("Error on validateToken", e);
					SecurityContextHolder.clearContext();
					throw e;
				}
				
			} else {
				log.info("Not found Bearer Token");
			}

		} else {
			log.info("Not found Authentication Header");
		}
		
		filterChain.doFilter(request, response);

	}

}
