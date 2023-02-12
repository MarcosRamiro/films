package com.ramiro.films.adapter.config;

import com.ramiro.films.adapter.handler.MyErrorHandler;
import com.ramiro.films.adapter.resource.RestClient;
import com.ramiro.films.adapter.resource.impl.RestClientImpl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigApp {

    @Bean
    public RestClient restClient(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setErrorHandler(new MyErrorHandler());
        return new RestClientImpl("https://www.omdbapi.com/?apikey=796c1a0a", restTemplate);
    }
}
