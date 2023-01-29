package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.model.Film;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmDto {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Released")
    private String released;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Actors")
    private String actors;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("imdbID")
    private String imdbID;

    public static FilmDto of(Film film) {

        return new FilmDto(film.getTitle(), film.getYear(), film.getReleased(), film.getDirector(),
                film.getActors(), film.getYear(), film.getImdbRating(), film.getImdbId());
    }

}
