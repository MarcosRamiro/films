package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.LoginDto;
import com.ramiro.films.domain.exceptions.LoginNotFoundException;
import com.ramiro.films.domain.usecase.FindLoginUseCase;
import com.ramiro.films.domain.usecase.repository.AllLogins;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class FindLoginUseCaseImpl implements FindLoginUseCase {

    private final AllLogins allLogins;

    @Inject
    public FindLoginUseCaseImpl(AllLogins allLogins) {
        this.allLogins = allLogins;
    }

    @Override
    public LoginDto byUsername(String username) throws LoginNotFoundException {
        return allLogins.findOpenLogin(username)
                .map(login -> new LoginDto(login.getToken()))
                .orElseThrow(() -> new LoginNotFoundException("has no login open by username: " + username));
    }
}
