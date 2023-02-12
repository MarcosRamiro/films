package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.domain.type.FilmOptionEnum;
import lombok.Data;

@Data
public class MoveRequest {

    @JsonProperty("filme_com_maior_avaliacao")
    public FilmOptionEnum filmeComMaiorAvaliacao;

}
