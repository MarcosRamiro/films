package com.ramiro.films.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	private final UserAuthenticationProvider authenticationProvider;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
				.and()
				.addFilterBefore(new UsernamePasswordAuthFilter(authenticationProvider), BasicAuthenticationFilter.class)
				.addFilterBefore(new JwtAuthFilter(authenticationProvider), UsernamePasswordAuthFilter.class)
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.headers().frameOptions().disable()
				.and()
				.authorizeRequests()
				.antMatchers("/api/login", "/h2-console/**").permitAll()
                .anyRequest().authenticated();

	}

}
