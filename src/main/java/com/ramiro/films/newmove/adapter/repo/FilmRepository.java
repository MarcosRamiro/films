package com.ramiro.films.newmove.adapter.repo;

import com.ramiro.films.model.Film;
import com.ramiro.films.newmove.usecase.AllFilms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends AllFilms, JpaRepository<Film, Long> {

    @Override
    default List<Film> findAllFilms(){
        return this.findAll();
    }
}
