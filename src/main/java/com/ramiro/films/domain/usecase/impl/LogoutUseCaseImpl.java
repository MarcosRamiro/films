package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.type.StatusLoginEnum;
import com.ramiro.films.domain.usecase.LogoutUseCase;
import com.ramiro.films.domain.usecase.repository.AllLogins;
import com.ramiro.films.domain.usecase.repository.AllUsers;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class LogoutUseCaseImpl implements LogoutUseCase {

    private final AllLogins allLogins;
    private final AllUsers allUsers;

    @Override
    public void signOut(UserDto userDto) {

        allLogins.updateLogin(StatusLoginEnum.CLOSED, StatusLoginEnum.OPEN,
                allUsers.findUserByUsername(userDto.getUsername()).get().getId());

    }
}
