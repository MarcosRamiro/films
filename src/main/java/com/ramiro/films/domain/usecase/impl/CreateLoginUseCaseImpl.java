package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.entity.model.Login;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.type.StatusLoginEnum;
import com.ramiro.films.domain.usecase.CreateLoginUseCase;
import com.ramiro.films.domain.usecase.repository.AllLogins;
import com.ramiro.films.domain.usecase.repository.AllUsers;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class CreateLoginUseCaseImpl implements CreateLoginUseCase {

    private final AllLogins allLogins;
    private final AllUsers allUsers;

    @Override
    public void save(UserDto userDto) {

        User user = allUsers.findUserByUsername(userDto.getUsername()).get();

        Login login = new Login(user, userDto.getToken());
        allLogins.updateLogin(StatusLoginEnum.CLOSED, StatusLoginEnum.OPEN,user.getId());
        allLogins.saveLogin(login);

    }
}
