package com.ramiro.films.service.impl;

import com.ramiro.films.service.RestClient;

public class RestClientBuilder {

    private final RestClient restClient;
    private String query;

    public RestClientBuilder(RestClient restClient, String query) {
        this.restClient = restClient;
        this.query = sanitizeQuery(query);
    }

    public RestClientBuilder query(String query) {
        this.query += sanitizeQuery(query);
        return this;
    }

    public <T> T get(Class<T> classz) {
        return restClient.get(query, classz);
    }

    private String sanitizeQuery(String query) {
        return "&" + query;
    }

}
