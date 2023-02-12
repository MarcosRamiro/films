package com.ramiro.films.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramiro.films.domain.entity.model.User;
import lombok.Data;

@Data
public class UserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("token")
    private String token;

    private UserRequest() {
    }

    private UserRequest(String username, User user) {
        this.username = username;
    }

}
