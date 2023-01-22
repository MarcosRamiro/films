package com.ramiro.films.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FilmSearchDto {

	@JsonProperty("Search")
	private List<FilmDto> films;

	@JsonProperty("totalResults")
	private String totalResults;

	@JsonProperty("Response")
	private String response;

	public boolean succeed() {
		return this.response.equalsIgnoreCase("True");
	}

}
