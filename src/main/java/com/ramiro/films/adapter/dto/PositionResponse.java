package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PositionResponse {

    @JsonProperty("posicao")
    int position;

    @JsonProperty("usuario")
    String username;

    @JsonProperty("pontos")
    int points;

}
