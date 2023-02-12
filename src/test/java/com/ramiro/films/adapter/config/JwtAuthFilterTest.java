package com.ramiro.films.adapter.config;

import com.ramiro.films.domain.entity.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

    @InjectMocks
    JwtAuthFilter jwtAuthFilter;

    @Mock
    UserAuthenticationProvider provider;

    @Test
    void naoDevePermitirSeguirComAValidacaoQuandoNaoHaHearderAuthorization() throws ServletException, IOException {

        FilterChain chainMock = mock(FilterChain.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader(any())).thenReturn(null);
        when(request.getServletPath()).thenReturn("/uri");

        jwtAuthFilter.doFilterInternal(request, responseMock, chainMock);

        verify(provider, times(0)).validateToken(any());
        verify(chainMock, times(1)).doFilter(any(), any());

    }

    @Test
    void naoDevePermitirSeguirComAValidacaoQuandoTokenNaoForBearer() throws ServletException, IOException {
        FilterChain chainMock = mock(FilterChain.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);

        when(requestMock.getMethod()).thenReturn("GET");
        when(requestMock.getHeader(any())).thenReturn("TOKEN_INVALIDO");
        when(requestMock.getServletPath()).thenReturn("/uri");

        jwtAuthFilter.doFilterInternal(requestMock, responseMock, chainMock);

        verify(provider, times(0)).validateToken(any());
        verify(chainMock, times(1)).doFilter(requestMock, responseMock);

    }

    @Test
    void devePermitirAcessoQuandoHaTokenValidoNoHeader() throws ServletException, IOException {
        FilterChain chainMock = mock(FilterChain.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(mock(UserDto.class), null, Collections.emptyList());
        ;

        when(provider.validateToken(any())).thenReturn(authentication);
        when(requestMock.getHeader(any())).thenReturn("Bearer 123456");

        jwtAuthFilter.doFilterInternal(requestMock, responseMock, chainMock);

        verify(provider, times(1)).validateToken(any());
        verify(chainMock, times(1)).doFilter(requestMock, responseMock);

    }

    @Test
    void deveGerarQuandoProviderEstourarErro() throws ServletException, IOException {
        FilterChain chainMock = mock(FilterChain.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);

        when(requestMock.getHeader(any())).thenReturn("Bearer 123456");
        when(provider.validateToken(any())).thenThrow(new RuntimeException("Deu Erro"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            jwtAuthFilter.doFilterInternal(requestMock, responseMock, chainMock);;
        });

        assertEquals("Deu Erro", exception.getMessage());
        verify(chainMock, times(0)).doFilter(any(), any());

    }

}
