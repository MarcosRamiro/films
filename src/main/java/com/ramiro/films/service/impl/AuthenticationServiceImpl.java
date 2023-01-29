package com.ramiro.films.service.impl;

import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.repository.UserRepository;
import com.ramiro.films.service.AuthenticationService;
import com.ramiro.films.type.StatusLoginEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    @Override
    public User validateCredentials(CredentialsRequest credentials) {
        log.debug("Start Login validate credentials: " + credentials);
        User user = this.findByUsername(credentials.getUsername());

        if (isUserValid(user, credentials)) {
            log.info("Login efetuado com sucesso. username: " + user.getUsername());
            return user;
        }

        log.info("invalid credentials");
        throw new AuthenticationCredentialsNotFoundException("invalid username/password");
    }

    private boolean isUserValid(User user, CredentialsRequest credentials) {
        return user.getPassword().equals(credentials.getPassword());
    }

    @Override
    public User findByUsername(String username) {

        Optional<User> userOptional = userRepository.findTop1ByUsername(username);

        if (userOptional.isPresent())
            return userOptional.get();

        log.info(String.format("user not found: %s.", username));
        throw new AuthenticationCredentialsNotFoundException("invalide username");

    }

    @Override
    public Login findLogin(User user) {

        Optional<Login> loginOptional = loginRepository.findTop1ByUserAndStatus(user, StatusLoginEnum.OPEN);
        if (loginOptional.isPresent())
            return loginOptional.get();

        log.info("login not found");

        throw new AuthenticationCredentialsNotFoundException("login not found");
    }

}
