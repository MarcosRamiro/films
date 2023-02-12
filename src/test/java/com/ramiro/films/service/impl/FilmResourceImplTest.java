package com.ramiro.films.service.impl;

import com.ramiro.films.adapter.infra.resource.RestClientBuilder;
import com.ramiro.films.domain.entity.dto.FilmDto;
import com.ramiro.films.domain.entity.dto.FilmSearchDto;
import com.ramiro.films.adapter.infra.resource.impl.FilmResourceImpl;
import com.ramiro.films.adapter.infra.resource.RestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmResourceImplTest {

    @InjectMocks
    private FilmResourceImpl filmResource;

    @Mock
    RestClient restClient;

    @Test
    public void devePesquisarFilmPorId() {

        FilmDto film = new FilmDto("Na estrada", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "movie", "9.00", "4");

        RestClientBuilder mockRestClientBuilder = mock(RestClientBuilder.class);
        when(mockRestClientBuilder.get(any())).thenReturn(film);
        when(restClient.query(any())).thenReturn(mockRestClientBuilder);

        FilmDto filmResultado = filmResource.getFilmById("4");

        assertEquals(film, filmResultado);
        verify(mockRestClientBuilder, times(1)).get(any());

    }

    @Test
    public void devePesquisarFilmsPorTitulo() {

        FilmSearchDto filmSearchDto1 = new FilmSearchDto();
        filmSearchDto1.setResponse("True");
        filmSearchDto1.setTotalResults("1");
        List<FilmDto> films1 = new ArrayList<>();
        FilmDto film1 = new FilmDto("Na estrada", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "movie", "9.00", "4");
        films1.add(film1);
        filmSearchDto1.setFilms(films1);

        FilmSearchDto filmSearchDto2 = new FilmSearchDto();
        filmSearchDto2.setResponse("True");
        filmSearchDto2.setTotalResults("1");
        List<FilmDto> films2 = new ArrayList<>();
        FilmDto film2 = new FilmDto("Pelé", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "movie", "9.00", "9");
        FilmDto naoehFilm = new FilmDto("Pelé", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "serie", "9.00", "9");

        films2.add(film2);
        films2.add(naoehFilm);
        filmSearchDto2.setFilms(films2);

        RestClientBuilder mockRestClientBuilder = mock(RestClientBuilder.class);
        when(mockRestClientBuilder.get(any())).thenReturn(filmSearchDto1, filmSearchDto2);
        when(restClient.query(any())).thenReturn(mockRestClientBuilder);
        when(mockRestClientBuilder.query(any())).thenReturn(mockRestClientBuilder);

        List<FilmDto> filmsResultado = filmResource.searchFilmByTitle("xpto");

        boolean deveSerTrue = filmsResultado.stream()
                .allMatch(film -> film.getImdbID() == film1.getImdbID()
                        ||
                        film.getImdbID() == film2.getImdbID());

        assertTrue(deveSerTrue);
        assertEquals(2, filmsResultado.size());
        verify(mockRestClientBuilder, times(2)).get(any());

    }


}
