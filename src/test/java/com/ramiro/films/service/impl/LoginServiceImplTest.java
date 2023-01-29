package com.ramiro.films.service.impl;

import com.ramiro.films.dto.UserDto;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.type.StatusLoginEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

    @InjectMocks
    LoginServiceImpl loginService;

    @Mock
    LoginRepository loginRepository;

    @Test
    void deveFinalizarLoginsAbertosQuandoNovoLoginEfetuado() {

        User user = new User();
        user.setUsername("maria");
        user.setId(1L);
        UserDto userDto = UserDto.of(user);

        loginService.save(userDto);

        verify(loginRepository, times(1))
                .updateStatusAndDateTimeEndByStatusAndUser(StatusLoginEnum.CLOSED.toString(), StatusLoginEnum.OPEN.toString(), user.getId());
        verify(loginRepository, times(1)).save(any());

    }

    @Test
    void deveFazerLogout() {

        User user = new User();
        user.setUsername("maria");
        user.setId(1L);
        UserDto userDto = UserDto.of(user);

        loginService.logout(userDto);

        verify(loginRepository, times(1))
                .updateStatusAndDateTimeEndByStatusAndUser(StatusLoginEnum.CLOSED.toString(), StatusLoginEnum.OPEN.toString(), user.getId());
        verify(loginRepository, times(0)).save(any());

    }

}
