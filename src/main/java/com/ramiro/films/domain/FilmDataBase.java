package com.ramiro.films.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ramiro.films.dto.Film;
import com.ramiro.films.service.FilmService;

@Component
public class FilmDataBase {
	private final Set<Film> films;
	private final FilmService filmService;
	private final String[] WORDS_TO_SEARCH_FILMS = new String[]{"war","love","sex","york","amor","guerra","paz"};
	
	Logger logger = LoggerFactory.getLogger(FilmDataBase.class);
	
	public FilmDataBase(FilmService filmService) {
		this.films = new HashSet<>();
		this.filmService = filmService;
		uploadInitialFilms();
	}
	
	public Set<Film> getAllFilms() {
		return this.films;
	}
	
	private void uploadInitialFilms() {
		logger.info("Searching Films...");
		List<Film> searchFilmByTitle = this.filmService.searchFilmByTitle(WORDS_TO_SEARCH_FILMS[getRandomNumber()]);
		logger.info("Films Found: " + searchFilmByTitle.size());
		List<Film> listFilms =
								searchFilmByTitle.stream()
									.map(f -> this.filmService.getFilmById(f.imdbID()))
									.filter(f -> !f.imdbRating().equals("N/A"))
									.toList();
		films.addAll(listFilms);
		logger.info("Films in Memory: " + films.size());
		logger.info("Finished Search Films...");
	}
	
	private int getRandomNumber() {
		int min = 0;
		int max = WORDS_TO_SEARCH_FILMS.length-1;
	    return (int) ((Math.random() * (max - min)) + min);
	}

}
