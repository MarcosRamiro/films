package com.ramiro.films.domain.entity.dto;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String token;

    private UserDto() {
    }

    public UserDto(String username) {
        this.username = username;
    }

}
