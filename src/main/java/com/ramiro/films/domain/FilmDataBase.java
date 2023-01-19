package com.ramiro.films.domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ramiro.films.dao.Film;
import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.repository.FilmRepository;
import com.ramiro.films.service.FilmService;

@Component
public class FilmDataBase {

	private final FilmService filmService;
	private final FilmRepository filmRepository;
	
	private Queue<String> WORDS_TO_SEARCH_FILMS = new LinkedList<>();
	
	Logger logger = LoggerFactory.getLogger(FilmDataBase.class);
	
	public FilmDataBase(FilmService filmService, FilmRepository filmRepository) {
		this.filmService = filmService;
		this.filmRepository = filmRepository;
		setupWords();
		uploadInitialFilms();
	}
	
	private void setupWords() {
		WORDS_TO_SEARCH_FILMS.addAll(Arrays.asList("paz","war","love","sex","york","amor","guerra"));
	}

	public List<Film> getAllFilms() {
		return this.filmRepository.findAll();
	}
	
	public List<Film> getTwoFilms() {
		return this.filmRepository.findTop2ByOrderByTitleAsc();
	}
	
	private void uploadInitialFilms() {
		logger.info("Searching Films...");
		List<FilmDto> searchFilmByTitle = this.filmService.searchFilmByTitle(WORDS_TO_SEARCH_FILMS.poll());
		logger.info("Films Found: " + searchFilmByTitle.size());
		List<FilmDto> listFilms =
								searchFilmByTitle.stream()
									.map(f -> this.filmService.getFilmById(f.imdbID()))
									.filter(f -> !f.imdbRating().equals("N/A"))
									.toList();
		listFilms.stream().forEach(f -> filmRepository.save(Film.of(f)));
		logger.info("Films in DataBase: " + filmRepository.count());
		logger.info("Finished Search Films...");
	}
	
}
