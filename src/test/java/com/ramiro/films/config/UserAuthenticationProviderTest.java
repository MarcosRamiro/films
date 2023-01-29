package com.ramiro.films.config;

import com.ramiro.films.dto.CredentialsRequest;
import com.ramiro.films.dto.UserDto;
import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;
import com.ramiro.films.service.AuthenticationService;
import io.jsonwebtoken.Jwts;
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
    AuthenticationService authenticationService;

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
    void deveValidarToken() {

        String username = "maria";
        Login login = new Login();
        User user = new User();
        user.setUsername("maria");

        when(authenticationService.findByUsername(anyString())).thenReturn(user);
        when(authenticationService.findLogin(any())).thenReturn(login);
        String token = userAuthenticationProvider.createToken(username);
        login.setToken(token);

        Authentication authentication = userAuthenticationProvider.validateToken(token);

        assertEquals(user.getUsername(), ((UserDto) authentication.getPrincipal()).getUsername());
        verify(authenticationService, times(1)).findByUsername(anyString());
        verify(authenticationService, times(1)).findLogin(any());

    }

    @Test
    void deveGerarErroQuandoUsuarioNaoLogado() {

        Login login = new Login();
        login.setToken("123");
        String token = userAuthenticationProvider.createToken("maria");

        when(authenticationService.findByUsername(anyString())).thenReturn(mock(User.class));
        when(authenticationService.findLogin(any())).thenReturn(login);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userAuthenticationProvider.validateToken(token);
        });

        assertEquals("token expired by logout", exception.getMessage());
        verify(authenticationService, times(1)).findByUsername(anyString());
        verify(authenticationService, times(1)).findLogin(any());

    }

    @Test
    void deveValidarCredenciais() {

        CredentialsRequest credentials = new CredentialsRequest();
        User user = new User();
        user.setUsername("maria");

        when(authenticationService.validateCredentials(any())).thenReturn(user);

        Authentication authentication = userAuthenticationProvider.validadeCredentials(credentials);

        assertEquals(user.getUsername(), ((UserDto) authentication.getPrincipal()).getUsername());
        verify(authenticationService, times(1)).validateCredentials(any());

    }

}
