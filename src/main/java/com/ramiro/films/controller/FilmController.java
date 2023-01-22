package com.ramiro.films.controller;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.films.domain.FilmDataBase;
import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.service.FilmService;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

	private final FilmService filmService;
	private final FilmDataBase filmDataBase;

	@GetMapping("/id")
	public FilmDto getFilmById(@RequestParam String id) {
		return filmService.getFilmById(id);

	}

	@GetMapping("/title")
	public List<FilmDto> searchFilmByTitle(@RequestParam String title) {
		log.info("searcing by title: " + title);
		return filmDataBase.getAllFilms().stream().map(f -> FilmDto.of(f)).collect(Collectors.toList());

	}

}
