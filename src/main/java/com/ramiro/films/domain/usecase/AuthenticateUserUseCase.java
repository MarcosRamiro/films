package com.ramiro.films.domain.usecase;

import com.ramiro.films.domain.entity.dto.CredentialsDto;

public interface AuthenticateUserUseCase {

    boolean validateCredentials(CredentialsDto credentials);

}
