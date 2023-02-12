package com.ramiro.films.adapter.config;

import com.ramiro.films.adapter.dto.CredentialsRequest;
import com.ramiro.films.domain.entity.dto.LoginDto;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.exceptions.LoginNotFoundException;
import com.ramiro.films.domain.exceptions.UserNotFoundException;
import com.ramiro.films.domain.usecase.AuthenticateUserUseCase;
import com.ramiro.films.domain.usecase.FindLoginUseCase;
import com.ramiro.films.domain.usecase.FindUserUseCase;
import io.jsonwebtoken.Jwts;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserAuthenticationProvider.class)
public class UserAuthenticationProviderTest {

    @Autowired
    UserAuthenticationProvider userAuthenticationProvider;

    @MockBean
    FindUserUseCase findUserUseCase;

    @MockBean
    FindLoginUseCase findLoginUseCase;

    @MockBean
    AuthenticateUserUseCase authenticateUserUseCase;

    private String secretKey =
            Base64.getEncoder().encodeToString("my-secret".getBytes());

    @Test
    void deveCriarTokenValido() {

        String username = "maria";

        String token = userAuthenticationProvider.createToken(username);

        String usernameDoToken = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        assertEquals(username, usernameDoToken);

    }

    @Test
    void deveValidarToken() throws AuthenticationException, UserNotFoundException, LoginNotFoundException {

        String username = "maria";
        UserDto user = new UserDto(username);

        when(findUserUseCase.byUsername(anyString())).thenReturn(user);
        String token = userAuthenticationProvider.createToken(username);
        LoginDto login = new LoginDto(token);
        when(findLoginUseCase.byUsername(anyString())).thenReturn(login);

        Authentication authentication = userAuthenticationProvider.validateToken(token);

        assertEquals(user.getUsername(), ((UserDto) authentication.getPrincipal()).getUsername());
        //verify(authenticationService, times(1)).findByUsername(anyString());
        //verify(authenticationService, times(1)).findLogin(any());

    }

    @Test
    void deveGerarErroQuandoUsuarioNaoLogado() throws UserNotFoundException, LoginNotFoundException {

        LoginDto login = new LoginDto("123");
        String token = userAuthenticationProvider.createToken("maria");

        when(findUserUseCase.byUsername(anyString())).thenReturn(mock(UserDto.class));
        when(findLoginUseCase.byUsername(any())).thenReturn(login);

        Exception exception = assertThrows(Exception.class, () -> {
            userAuthenticationProvider.validateToken(token);
        });

        assertEquals("token expired by logout", exception.getMessage());
        verify(findUserUseCase, times(1)).byUsername(anyString());
        verify(findLoginUseCase, times(1)).byUsername(any());

    }

    @Test
    void deveValidarCredenciais() throws UserNotFoundException, AuthenticationException {

        CredentialsRequest credentials = new CredentialsRequest();
        UserDto user = new UserDto("maria");

        when(authenticateUserUseCase.validateCredentials(any())).thenReturn(true);
        when(findUserUseCase.byUsername(any())).thenReturn(user);

        Authentication authentication = userAuthenticationProvider.validadeCredentials(credentials);

        assertEquals(user.getUsername(), ((UserDto) authentication.getPrincipal()).getUsername());
        verify(authenticateUserUseCase, times(1)).validateCredentials(any());

    }

}
