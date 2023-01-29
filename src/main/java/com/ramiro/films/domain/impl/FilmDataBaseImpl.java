package com.ramiro.films.domain.impl;

import com.ramiro.films.domain.FilmDataBase;
import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.model.Film;
import com.ramiro.films.repository.FilmRepository;
import com.ramiro.films.service.FilmService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class FilmDataBaseImpl implements FilmDataBase {

    private final FilmService filmService;
    private final FilmRepository filmRepository;

    @Getter
    private static Queue<String> WORDS_TO_SEARCH_FILMS = new LinkedList<>();

    static {
        WORDS_TO_SEARCH_FILMS.addAll(Arrays.asList("paz", "war", "love", "sex", "york", "amor", "guerra"));
    }

    @PostConstruct
    public void uploadInitialFilms() {
        uploadFilms();
    }

    public void uploadFilms() {

        log.info("Searching Films...");
        String word = WORDS_TO_SEARCH_FILMS.poll();

        if (word == null)
            throw new RuntimeException("word list is empty");

        List<FilmDto> searchFilmByTitle = this.filmService.searchFilmByTitle(word);
        log.info("Films Found: " + searchFilmByTitle.size());
        List<FilmDto> listFilms =
                searchFilmByTitle.stream()
                        .map(f -> this.filmService.getFilmById(f.getImdbID()))
                        .filter(f -> !f.getImdbRating().equals("N/A"))
                        .collect(Collectors.toList());
        listFilms.stream().forEach(f -> filmRepository.save(Film.of(f)));
        log.info("Films in DataBase: " + filmRepository.count());
        log.info("Finished Search Films...");
    }

}
