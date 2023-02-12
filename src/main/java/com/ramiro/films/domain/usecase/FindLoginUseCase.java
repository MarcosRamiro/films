package com.ramiro.films.domain.usecase;

import com.ramiro.films.domain.entity.dto.LoginDto;
import com.ramiro.films.domain.exceptions.LoginNotFoundException;

public interface FindLoginUseCase {

    LoginDto byUsername(String username) throws LoginNotFoundException;

}
