package com.ramiro.films.adapter.config;

import com.ramiro.films.adapter.dto.CredentialsRequest;
import com.ramiro.films.domain.entity.dto.LoginDto;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.exceptions.LoginNotFoundException;
import com.ramiro.films.domain.exceptions.UserNotFoundException;
import com.ramiro.films.domain.usecase.AuthenticateUserUseCase;
import com.ramiro.films.domain.usecase.FindLoginUseCase;
import com.ramiro.films.domain.usecase.FindUserUseCase;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Data
@Component
@Slf4j
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final FindUserUseCase findUserUseCase;
    private final FindLoginUseCase findLoginUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        log.info("createToken");
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

    public Authentication validateToken(String token) throws AuthenticationException {
        log.info("validateToken");
        String username = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        try {

            LoginDto login = findLoginUseCase.byUsername(username);

            if (!isTokenActive(login, token))
                throw new AuthenticationException("token expired by logout");

            UserDto user = findUserUseCase.byUsername(username);
            user.setToken(token);
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        } catch (UserNotFoundException | LoginNotFoundException e) {
            e.printStackTrace();
            throw new AuthenticationException("error", e);
        }

    }

    public Authentication validadeCredentials(CredentialsRequest credentials)
            throws UserNotFoundException, AuthenticationException {

        if (authenticateUserUseCase.validateCredentials(credentials)){
            UserDto user = findUserUseCase.byUsername(credentials.getUsername());
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        }

        throw new AuthenticationException("error");
    }

    private boolean isTokenActive(LoginDto login, String token) {
        return login.getToken().equals(token);
    }

}
