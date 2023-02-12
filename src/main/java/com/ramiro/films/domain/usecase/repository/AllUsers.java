package com.ramiro.films.domain.usecase.repository;

import com.ramiro.films.domain.entity.model.User;

import java.util.Optional;

public interface AllUsers {

    Optional<User> findUserByUsername(String username);
}
