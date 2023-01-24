package com.ramiro.films.service.impl;

import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.dto.FilmSearchDto;
import com.ramiro.films.service.FilmService;
import com.ramiro.films.service.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmServiceImpl implements FilmService {

    private final RestClient restClient;
    private static final int LIMIT = 2;
    private static final String TYPE_MOVIE = "movie";

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
            films.addAll(filmSearch.getFilms().stream().filter((f -> f.getType().equals(TYPE_MOVIE))).collect(Collectors.toList()));
            String queryPage = "page=" + ++page;
            filmSearch = restClient
                    .query(querySearch)
                    .query(queryPage)
                    .get(FilmSearchDto.class);
        }

        return films;
    }

    @Override
    public FilmDto getFilmById(String id) {
        log.info("Get film by id: " + id);
        String query = "i=" + id;
        return restClient.query(query).get(FilmDto.class);
    }

}
