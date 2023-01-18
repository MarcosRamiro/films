package com.ramiro.films.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Film(String title, 
					String year,
					String released,
					String director, 
					String actors, 
					String type, 
					String imdbRating,
					String imdbID) {

	@JsonCreator
	public Film(
			@JsonProperty("Title") String title,
			@JsonProperty("Year") String year,
			@JsonProperty("Released") String released,
			@JsonProperty("Director") String director,
			@JsonProperty("Actors") String actors,
			@JsonProperty("Type") String type,
			@JsonProperty("imdbRating") String imdbRating,
			@JsonProperty("imdbID") String imdbID
			) {

		this.title = title;
		this.year = year;
		this.released = released;
		this.director = director;
		this.actors = actors;
		this.type = type;
		this.imdbRating = imdbRating;
		this.imdbID = imdbID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actors, director, imdbRating, released, title, type, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(actors, other.actors) && Objects.equals(director, other.director)
				&& Objects.equals(imdbRating, other.imdbRating) && Objects.equals(released, other.released)
				&& Objects.equals(title, other.title) && Objects.equals(type, other.type)
				&& Objects.equals(year, other.year);
	}

}
