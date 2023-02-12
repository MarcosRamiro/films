package com.ramiro.films.adapter.resource;

public interface RestClient {

    <T> T get(Class<T> classz);

    <T> T get(String query, Class<T> classz);

    RestClientBuilder query(String query);

}
