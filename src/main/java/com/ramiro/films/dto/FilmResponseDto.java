package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.model.Film;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmResponseDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("year")
    private String year;

    @JsonProperty("released")
    private String released;

    @JsonProperty("director")
    private String director;

    @JsonProperty("actors")
    private String actors;

    public static FilmResponseDto of(Film film){
        return new FilmResponseDto(film.getTitle(),film.getYear(), film.getReleased(), film.getDirector(), film.getActors());

    }

}
