package com.ramiro.films.service;

import com.ramiro.films.domain.entity.dto.UserDto;

public interface LoginService {
        void logout(UserDto user);
}
