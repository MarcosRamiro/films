package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PositionResponseDto {

    @JsonProperty("posicao")
    int position;

    @JsonProperty("usuario")
    String username;

    @JsonProperty("pontos")
    int points;

}
