package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.exceptions.UserNotFoundException;
import com.ramiro.films.domain.usecase.FindUserUseCase;
import com.ramiro.films.domain.usecase.repository.AllUsers;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class FindUserUseCaseImpl implements FindUserUseCase {

    private final AllUsers allUsers;

    @Inject
    public FindUserUseCaseImpl(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    @Override
    public UserDto byUsername(String username) throws UserNotFoundException {
        return allUsers.findUserByUsername(username)
                .map(user -> new UserDto(user.getUsername()))
                .orElseThrow(() -> new UserNotFoundException("username not found: " + username));

    }
}
