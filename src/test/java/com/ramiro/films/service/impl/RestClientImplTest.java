package com.ramiro.films.service.impl;

import com.ramiro.films.adapter.resource.RestClientBuilder;
import com.ramiro.films.adapter.resource.impl.RestClientImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestClientImplTest {


    RestClientImpl restClient;

    @Test
    void deveCriarBuilderComQuery() {
        RestTemplate restMock = mock(RestTemplate.class);
        restClient = new RestClientImpl("url", restMock);

        RestClientBuilder builder = restClient.query("queryQualquer");

        assertNotNull(builder);

    }

    @Test
    void deveEfetuarGetComQuery() {
        RestTemplate restMock = mock(RestTemplate.class);
        restClient = new RestClientImpl("url", restMock);

        when(restMock.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("deu bom", HttpStatus.OK));

        String retorno = restClient.get("queryqualquer", String.class);

        assertEquals("deu bom", retorno);
        verify(restMock, times(1)).getForEntity(anyString(), eq(String.class));

    }

    @Test
    void deveEfetuarGet() {
        RestTemplate restMock = mock(RestTemplate.class);
        restClient = new RestClientImpl("url", restMock);

        when(restMock.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("deu bom", HttpStatus.OK));

        String retorno = restClient.get(String.class);

        assertEquals("deu bom", retorno);
        verify(restMock, times(1)).getForEntity(anyString(), eq(String.class));

    }


}
