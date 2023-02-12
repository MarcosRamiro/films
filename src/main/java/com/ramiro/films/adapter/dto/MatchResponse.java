package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MatchResponse {

    @JsonProperty("id")
    public long id;

    @JsonProperty("mensagem")
    public String mensagem = "Partida iniciada. Vá para a 'Próxima jogada' para iniciar os lances.";

    public MatchResponse(long id) {
        this.id = id;
    }

}
