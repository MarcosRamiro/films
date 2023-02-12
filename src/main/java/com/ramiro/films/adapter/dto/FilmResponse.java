package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.domain.entity.dto.FilmDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmResponse {

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

    public static FilmResponse of(FilmDto film) {
        return new FilmResponse(film.getTitle(), film.getYear(), film.getReleased(), film.getDirector(), film.getActors());

    }

}
