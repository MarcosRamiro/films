package com.ramiro.films.service;

import com.ramiro.films.dto.FilmDto;

import java.util.List;

public interface FilmService {

    List<FilmDto> searchFilmByTitle(String title);

    FilmDto getFilmById(String id);

}
