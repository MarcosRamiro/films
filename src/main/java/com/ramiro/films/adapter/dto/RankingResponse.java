package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.adapter.dto.PositionResponse;
import lombok.Data;

import java.util.List;

@Data
public class RankingResponse {

    @JsonProperty("mensagem")
    public String message = ">>>>> Classificação <<<<< ";

    @JsonProperty("classificacao")
    public List<PositionResponse> ranking;

}
