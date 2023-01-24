package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class MatchResponseDto {

    @JsonProperty("id")
    public long id;

    @JsonProperty("mensagem")
    public String mensagem = "Partida iniciada. Vá para a 'Próxima jogada' para iniciar os lances.";

    public MatchResponseDto(long id){
        this.id = id;
    }

}
