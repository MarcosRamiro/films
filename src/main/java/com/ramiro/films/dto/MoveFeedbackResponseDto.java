package com.ramiro.films.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MoveFeedbackResponseDto {

    @JsonProperty("resultado")
    public String result;

    public MoveFeedbackResponseDto(String result) {
        this.result = result;
    }

}
