package com.ramiro.films.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.dto.FilmSearchDto;
import com.ramiro.films.service.FilmService;
import com.ramiro.films.service.RestClient;

@Service
public class FilmServiceImpl implements FilmService {
	
	private final RestClient restClient;
	private static final int LIMIT = 10;
	private static final String TYPE_MOVIE = "movie";
	
	Logger logger = LoggerFactory.getLogger(FilmServiceImpl.class);

	@Autowired
	public FilmServiceImpl(RestClient restClient) {
		this.restClient = restClient;
	}
	
	@Override
	public List<FilmDto> searchFilmByTitle(String title) {
		String querySearch = "s=" + title;
		int page = 1;
		FilmSearchDto filmSearch = restClient
								.query(querySearch)
								.get(FilmSearchDto.class);
		
		List<FilmDto> films = new ArrayList<>();

		while (filmSearch.succeed() && page < LIMIT) {
			films.addAll(filmSearch.films().stream().filter(f -> f.type().equals(TYPE_MOVIE)).toList());
			String queryPage =  "page=" + ++page;
			filmSearch = restClient
					.query(querySearch)
					.query(queryPage)
					.get(FilmSearchDto.class);
		}

		return films;
	}

	@Override
	public FilmDto getFilmById(String id) {
		logger.info("Get film by id: " + id);
		String query = "i=" + id;
		return restClient.query(query).get(FilmDto.class);
	}

}
