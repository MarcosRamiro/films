package com.ramiro.films.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PositionDto {

    private int position;
    private String username;
    private int points;
}
