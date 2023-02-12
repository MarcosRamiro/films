package com.ramiro.films.domain.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
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
