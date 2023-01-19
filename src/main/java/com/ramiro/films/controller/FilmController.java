package com.ramiro.films.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.films.domain.FilmDataBase;
import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.service.FilmService;

@RestController
@RequestMapping("/films")
public class FilmController {

	private final FilmService filmService;
	private final FilmDataBase filmDataBase;

	public FilmController(FilmService filmService, FilmDataBase filmDataBase) {
		this.filmService = filmService;
		this.filmDataBase = filmDataBase;
	}

	@GetMapping("/id")
	public FilmDto getFilmById(@RequestParam String id) {
		return filmService.getFilmById(id);

	}

	@GetMapping("/title")
	public List<FilmDto> searchFilmByTitle(@RequestParam String title) {
		return filmDataBase.getAllFilms().stream().map(f -> FilmDto.of(f)).toList();

	}

}
