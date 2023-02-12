package com.ramiro.films.adapter.resource.impl;

import com.ramiro.films.adapter.resource.RestClient;
import com.ramiro.films.adapter.resource.RestClientBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

public class RestClientImpl implements RestClient {

    private final String urlBase;
    private final RestTemplate restTemplate;
    private static final String EMPTY_VALUE = "";

    public RestClientImpl(String urlBase, RestTemplate restTemplate) {
        this.urlBase = urlBase;
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T get(String query, Class<T> classz) {

        String uri = this.urlBase + query;

        ResponseEntity<T> entity = restTemplate.getForEntity(uri, classz);

        return nonNull(entity.getBody());

    }

    @Override
    public <T> T get(Class<T> classz) {

        return nonNull(this.get(EMPTY_VALUE, classz));

    }

    private static <T> T nonNull(@Nullable T result) {
        Assert.state(result != null, "No result");
        return result;
    }

    @Override
    public RestClientBuilder query(String query) {

        return new RestClientBuilder(this, query);

    }

}
