package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.adapter.dto.PositionResponse;
import com.ramiro.films.domain.entity.dto.RankingDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RankingResponse {

    @JsonProperty("mensagem")
    public String message = ">>>>> Classificação <<<<<";

    @JsonProperty("classificacao")
    public List<PositionResponse> positions;

    public static RankingResponse of(RankingDto rankingDto){
        RankingResponse response = new RankingResponse();
        response.setPositions(
                rankingDto.getPositions().stream()
                        .map(PositionResponse::of)
                        .collect(Collectors.toList()));
        return response;
    }

}
