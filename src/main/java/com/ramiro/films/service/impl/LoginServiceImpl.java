package com.ramiro.films.service.impl;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.entity.model.Login;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.adapter.infra.repository.LoginRepository;
import com.ramiro.films.service.LoginService;
import com.ramiro.films.domain.type.StatusLoginEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;



    @Override
    public void logout(UserDto userDto) {

        //User user = userDto.getUser();
        //this.updateStatusLogin(StatusLoginEnum.CLOSED, StatusLoginEnum.OPEN, user);

    }

}
