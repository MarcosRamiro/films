package com.ramiro.films.service.impl;

import com.ramiro.films.dto.UserDto;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.service.LoginService;
import com.ramiro.films.type.StatusLoginEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    @Override
    public void save(UserDto userDto) {

        User user = userDto.getUser();
        this.updateStatusLogin(StatusLoginEnum.CLOSED, StatusLoginEnum.OPEN, user);
        Login login = new Login(user, userDto.getToken());
        loginRepository.save(login);

    }

    @Override
    public void logout(UserDto userDto) {

        User user = userDto.getUser();
        this.updateStatusLogin(StatusLoginEnum.CLOSED, StatusLoginEnum.OPEN, user);

    }

    private void updateStatusLogin(StatusLoginEnum statusToBe, StatusLoginEnum statusAsIs, User user) {
        loginRepository.updateStatusAndDateTimeEndByStatusAndUser(statusToBe.toString(), statusAsIs.toString(), user.getId());
    }

}
