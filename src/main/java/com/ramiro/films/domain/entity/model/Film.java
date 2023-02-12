package com.ramiro.films.domain.entity.model;

import com.ramiro.films.domain.entity.dto.FilmDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "film")
@NoArgsConstructor
@Data
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "year_film")
    private String year;

    @Column(name = "released")
    private String released;

    @Column(name = "director")
    private String director;

    @Column(name = "actors")
    private String actors;

    @Column(name = "imdbRating")
    private String imdbRating;

    @Column(name = "imdbId")
    private String imdbId;

    public Film(
            String title,
            String year,
            String released,
            String director,
            String actors,
            String imdbRating,
            String imdbId) {
        this.title = title;
        this.year = year;
        this.released = released;
        this.director = director;
        this.actors = actors;
        this.imdbRating = imdbRating;
        this.imdbId = imdbId;
    }

    public static Film of(FilmDto filmDto) {

        return new Film(
                filmDto.getTitle(),
                filmDto.getYear(),
                filmDto.getReleased(),
                filmDto.getDirector(),
                filmDto.getActors(),
                filmDto.getImdbRating(),
                filmDto.getImdbID()
        );

    }

    public FilmDto toDto() {
        return FilmDto.of(this);
    }

}
