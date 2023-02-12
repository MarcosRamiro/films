package com.ramiro.films.domain.usecase.repository;

import com.ramiro.films.domain.entity.model.Login;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.type.StatusLoginEnum;

import java.util.Optional;

public interface AllLogins {

    Optional<Login> findOpenLogin(String username);

    void saveLogin(Login login);

    void updateLogin(StatusLoginEnum statusToBe, StatusLoginEnum statusAsIs, long user_id);
}
