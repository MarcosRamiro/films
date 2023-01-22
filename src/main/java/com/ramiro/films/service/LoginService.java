package com.ramiro.films.service;

import com.ramiro.films.dto.UserDto;

public interface LoginService {

    void save(UserDto userDto);

    void logout(UserDto user);
}
