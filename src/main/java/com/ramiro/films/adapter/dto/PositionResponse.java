package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.domain.entity.dto.PositionDto;
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

    public static PositionResponse of(PositionDto positionDto) {
        PositionResponse response = new PositionResponse(positionDto.getPosition(), positionDto.getUsername(), positionDto.getPoints() );
        return response;

    }
}
