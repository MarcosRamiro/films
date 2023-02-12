package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.domain.entity.dto.MoveDto;
import lombok.Data;

@Data
public class MoveResponse {

    @JsonProperty("mensagem")
    public String MENSAGEM = "Quais desses filmes teve a maior avaliação?";

    @JsonProperty("filme_A")
    public FilmResponse filmA;

    @JsonProperty("filme_B")
    public FilmResponse filmB;

    public static MoveResponse of(MoveDto move) {
        MoveResponse moveResponse = new MoveResponse();
        moveResponse.setFilmA(FilmResponse.of(move.getFilmA()));
        moveResponse.setFilmB(FilmResponse.of(move.getFilmB()));
        return moveResponse;
    }
}
