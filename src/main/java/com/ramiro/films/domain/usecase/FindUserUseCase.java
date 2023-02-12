package com.ramiro.films.domain.usecase;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.exceptions.UserNotFoundException;

public interface FindUserUseCase {

    UserDto byUsername(String username) throws UserNotFoundException;

}
