package com.ramiro.films.service;

import java.util.List;

import com.ramiro.films.dto.FilmDto;

public interface FilmService {
	
	List<FilmDto> searchFilmByTitle(String id);

	FilmDto getFilmById(String id);

}
