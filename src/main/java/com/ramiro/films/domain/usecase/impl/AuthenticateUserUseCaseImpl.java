package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.adapter.dto.CredentialsRequest;
import com.ramiro.films.domain.entity.dto.CredentialsDto;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.usecase.AuthenticateUserUseCase;
import com.ramiro.films.domain.usecase.repository.AllUsers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
@Slf4j
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final AllUsers allUsers;

    @Override
    public boolean validateCredentials(CredentialsDto credentials) {

        log.debug("Start Login validate credentials: " + credentials);
        User user = allUsers.findUserByUsername(credentials.getUsername()).get();

        if (isUserValid(user, credentials)) {
            log.info("Login efetuado com sucesso. username: " + user.getUsername());
            return true;
        }

        log.info("invalid credentials");
        return false;
    }

    private boolean isUserValid(User user, CredentialsDto credentials) {
        return user.getPassword().equals(credentials.getPassword());
    }

}
