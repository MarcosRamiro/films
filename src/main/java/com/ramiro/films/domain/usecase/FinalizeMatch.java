package com.ramiro.films.domain.usecase;

import com.ramiro.films.domain.entity.dto.UserDto;

public interface FinalizeMatch {

    void finalizeIfPresent(UserDto userDto);

}
