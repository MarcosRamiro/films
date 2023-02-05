package com.ramiro.films.newmove.adapter.api;

import com.ramiro.films.dto.FilmDto;

import java.util.List;

public interface FilmResource {

    List<FilmDto> searchFilmByTitle(String title);

    FilmDto getFilmById(String id);

}
