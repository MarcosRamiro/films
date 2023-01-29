package com.ramiro.films.service.impl;

import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.dto.MoveRequestDto;
import com.ramiro.films.handler.exceptions.MoveNotFoundException;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.LoginRepository;
import com.ramiro.films.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @InjectMocks
    AuthenticationServiceImpl authenticationServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    LoginRepository loginRepository;

    @Before("init")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void devePesquisarUsuarioPeloUsername(){

        User userMock = mock(User.class);
        Optional<User> userOptional = Optional.of(userMock);
        when(userRepository.findTop1ByUsername(any())).thenReturn(userOptional);

        User userResultado = authenticationServiceImpl.findByUsername("xpto");

        assertEquals(userMock, userResultado);
        verify(userRepository, times(1)).findTop1ByUsername(any());

    }

    @Test
    public void deveGerarErroQuandoNaoLocalizarUsuarioNoRepo(){

        when(userRepository.findTop1ByUsername(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationServiceImpl.findByUsername("xpto");
        });

        assertEquals("invalide username", exception.getMessage());

    }

    @Test
    public void deveValidarCredenciaisCorretas(){

        CredentialsRequest credentials = new CredentialsRequest();
        credentials.setUsername("maria");
        credentials.setPassword("123");
        User user = new User();
        user.setUsername("maria");
        user.setPassword("123");
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findTop1ByUsername(any())).thenReturn(userOptional);

        User userResultado = authenticationServiceImpl.validateCredentials(credentials);

        assertEquals(user, userResultado);
        verify(userRepository, times(1)).findTop1ByUsername(any());
    }

    @Test
    public void deveGerarErroQuandoCredenciaisInvalidas(){

        CredentialsRequest credentials = new CredentialsRequest();
        credentials.setUsername("maria");
        credentials.setPassword("xxx");
        User user = new User();
        user.setUsername("maria");
        user.setPassword("123");
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findTop1ByUsername(any())).thenReturn(userOptional);

        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationServiceImpl.validateCredentials(credentials);
        });

        assertEquals("invalid username/password", exception.getMessage());

    }

    @Test
    public void deveLocalizarLoginAbertoQuandoUsuarioJaLogado(){

        Login loginMock = mock(Login.class);
        Optional<Login> loginOptional = Optional.of(loginMock);
        when(loginRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(loginOptional);

        Login loginResultado = authenticationServiceImpl.findLogin(mock(User.class));

        assertEquals(loginMock, loginResultado);
        verify(loginRepository, times(1)).findTop1ByUserAndStatus(any(),any());
    }

    @Test
    public void deveGerarErroQuandoNaoLocalizarLogin(){

        when(loginRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            authenticationServiceImpl.findLogin(mock(User.class));
        });

        assertEquals("login not found", exception.getMessage());

    }

}
