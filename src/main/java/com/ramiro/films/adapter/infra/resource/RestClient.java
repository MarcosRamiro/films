package com.ramiro.films.adapter.infra.resource;

public interface RestClient {

    <T> T get(Class<T> classz);

    <T> T get(String query, Class<T> classz);

    RestClientBuilder query(String query);

}