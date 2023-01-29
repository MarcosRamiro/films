package com.ramiro.films.config;

import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.dto.UserDto;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;
import com.ramiro.films.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final AuthenticationService authenticationService;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public Authentication validateToken(String token) {

        String username = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        User user = authenticationService.findByUsername(username);
        Login login = authenticationService.findLogin(user);

        if (!isTokenActive(login, token))
            throw new RuntimeException("token expired by logout");

        return new UsernamePasswordAuthenticationToken(UserDto.of(user), null, Collections.emptyList());
    }

    public Authentication validadeCredentials(CredentialsRequest credentials) {
        User user = authenticationService.validateCredentials(credentials);
        return new UsernamePasswordAuthenticationToken(UserDto.of(user), null, Collections.emptyList());
    }

    private boolean isTokenActive(Login login, String token) {
        return login.getToken().equals(token);
    }

}
