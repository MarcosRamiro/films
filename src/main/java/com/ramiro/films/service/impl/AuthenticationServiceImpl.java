package com.ramiro.films.service.impl;

import com.ramiro.films.adapter.dto.CredentialsRequest;
import com.ramiro.films.domain.entity.model.Login;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.adapter.infra.repository.LoginRepository;
import com.ramiro.films.adapter.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

   // @Override




}
