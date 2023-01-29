package com.ramiro.films.domain.impl;

import com.ramiro.films.dto.FilmDto;
import com.ramiro.films.repository.FilmRepository;
import com.ramiro.films.service.FilmService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmDataBaseImplTest {

    @InjectMocks
    private FilmDataBaseImpl filmDataBaseImpl;

    @Mock
    FilmService filmService;

    @Mock
    FilmRepository filmRepository;

    @Before("init")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void deveAtualizarListaDeFilmes() {

        List<FilmDto> films = getFilmes();
        when(filmService.searchFilmByTitle(anyString())).thenReturn(films);
        when(filmService.getFilmById(anyString())).thenReturn(films.get(0));
        when(filmRepository.count()).thenReturn(1L);

        filmDataBaseImpl.uploadInitialFilms();

        verify(filmService, times(1)).searchFilmByTitle(anyString());
        verify(filmService, times(1)).getFilmById(anyString());
        verify(filmRepository, times(1)).save(any());
        verify(filmRepository, times(1)).count();

    }

    private List<FilmDto> getFilmes() {
        List<FilmDto> films = new ArrayList<>();
        FilmDto film = new FilmDto("Na estrada", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "movie", "9.00", "4");
        films.add(film);
        return films;
    }

    @Test
    @Order(2)
    public void deveGerarErroQuandoAListaDePalavrasAcabar() {

        List<FilmDto> films = getFilmes();
        int qtdePalavras = FilmDataBaseImpl.getWORDS_TO_SEARCH_FILMS().size();
        when(filmService.searchFilmByTitle(anyString())).thenReturn(films);
        when(filmService.getFilmById(anyString())).thenReturn(films.get(0));
        when(filmRepository.count()).thenReturn(1L);

        for (int i = 0; i < qtdePalavras; i++) {
            filmDataBaseImpl.uploadInitialFilms();
        }

        Exception exception = assertThrows(RuntimeException.class, () -> {
            filmDataBaseImpl.uploadInitialFilms();
        });

        assertEquals("word list is empty", exception.getMessage());
        verify(filmService, times(qtdePalavras)).searchFilmByTitle(anyString());
        verify(filmService, times(qtdePalavras)).getFilmById(anyString());
        verify(filmRepository, times(qtdePalavras)).save(any());
        verify(filmRepository, times(qtdePalavras)).count();
    }


}
