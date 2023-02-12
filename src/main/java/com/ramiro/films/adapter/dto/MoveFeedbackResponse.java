package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MoveFeedbackResponse {

    @JsonProperty("resultado")
    public String result;

    public MoveFeedbackResponse(String result) {
        this.result = result;
    }

}
