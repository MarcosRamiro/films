package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuizResponseDto {

    @JsonProperty("mensagem")
    public String MENSAGEM = "Quais desses filmes teve a maior avaliação?";

    @JsonProperty("filme_A")
    public FilmResponseDto filmA;

    @JsonProperty("filme_B")
    public FilmResponseDto filmB;
}
