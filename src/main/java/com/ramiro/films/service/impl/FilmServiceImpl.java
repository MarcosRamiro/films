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
    private static final String TYPE_MOVIE = "movie";
    private static final int LIMIT_DEFAULT = 2;

    @Autowired
    public FilmServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<FilmDto> searchFilmByTitle(String title) {
        String querySearch = "s=" + title;
        int page = 0;
        String queryPage;
        FilmSearchDto filmSearch;
        List<FilmDto> films = new ArrayList<>();

        do {
            queryPage = "page=" + ++page;
            filmSearch = restClient
                    .query(querySearch)
                    .query(queryPage)
                    .get(FilmSearchDto.class);

            if (filmSearch.succeed()) {
                films.addAll(filmSearch.getFilms().stream().filter((f -> f.getType().equals(TYPE_MOVIE))).collect(Collectors.toList()));
            }
        } while (page <= LIMIT_DEFAULT);

        return films;
    }

    @Override
    public FilmDto getFilmById(String id) {
        log.info("Get film by id: " + id);
        String query = "i=" + id;
        return restClient.query(query).get(FilmDto.class);
    }

}
