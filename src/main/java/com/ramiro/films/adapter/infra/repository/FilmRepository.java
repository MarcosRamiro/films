package com.ramiro.films.adapter.infra.repository;

import com.ramiro.films.domain.entity.model.Film;
import com.ramiro.films.domain.usecase.repository.AllFilms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends AllFilms, JpaRepository<Film, Long> {

    @Override
    default List<Film> findAllFilms(){
        return this.findAll();
    }
}
