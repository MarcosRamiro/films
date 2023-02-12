package com.ramiro.films.domain.usecase.repository;

import com.ramiro.films.domain.entity.model.Film;

import java.util.List;

public interface AllFilms {
    List<Film> findAllFilms();
}
