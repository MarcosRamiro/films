package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.newmove.entity.Move;
import lombok.Data;

@Data
public class MoveResponseDto {

    @JsonProperty("mensagem")
    public String MENSAGEM = "Quais desses filmes teve a maior avaliação?";

    @JsonProperty("filme_A")
    public FilmResponseDto filmA;

    @JsonProperty("filme_B")
    public FilmResponseDto filmB;

    public static MoveResponseDto of(Move move) {
        MoveResponseDto moveResponseDto = new MoveResponseDto();
        moveResponseDto.setFilmA(FilmResponseDto.of(move.getFilmA()));
        moveResponseDto.setFilmB(FilmResponseDto.of(move.getFilmB()));
        return moveResponseDto;
    }
}
