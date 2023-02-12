package com.ramiro.films.domain.usecase.resource;

import com.ramiro.films.domain.entity.dto.FilmDto;

import java.util.List;

public interface FilmResource {

    List<FilmDto> searchFilmByTitle(String title);

    FilmDto getFilmById(String id);

}
