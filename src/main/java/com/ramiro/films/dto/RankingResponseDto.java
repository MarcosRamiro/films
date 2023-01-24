package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RankingResponseDto {

    @JsonProperty("mensagem")
    public String message = ">>>>> Classificação <<<<< ";

    @JsonProperty("classificacao")
    public List<PositionResponseDto> ranking;

}
