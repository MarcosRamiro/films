package com.ramiro.films.service;

import com.ramiro.films.service.impl.RestClientBuilder;

public interface RestClient {
	
	<T> T get(Class<T> classz);
	
	<T> T get(String query, Class<T> classz);
		
	RestClientBuilder query(String query);

}
