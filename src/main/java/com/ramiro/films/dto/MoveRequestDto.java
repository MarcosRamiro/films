package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.type.FilmOptionEnum;
import lombok.Data;

@Data
public class MoveRequestDto {

    @JsonProperty("filme_com_maior_avaliacao")
    public FilmOptionEnum filmeComMaiorAvaliacao;

}
