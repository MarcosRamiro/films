package com.ramiro.films.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmSearchDto(List<FilmDto> films, 
						String totalResults,
						String response) {

	@JsonCreator
	public FilmSearchDto(
			@JsonProperty("Search") List<FilmDto> films,
			@JsonProperty("totalResults") String totalResults,
			@JsonProperty("Response") String response) {

		this.films = films;
		this.totalResults = totalResults;
		this.response = response;
	}
	
	public boolean succeed() {
		return this.response.equalsIgnoreCase("True");
	}

}
