package com.ramiro.films.domain;

import com.ramiro.films.model.Film;

import java.util.List;

public interface FilmDataBase {

    List<Film> getTwoFilms();

    void uploadFilms();

}
