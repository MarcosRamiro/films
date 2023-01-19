package com.ramiro.films.dao;

import com.ramiro.films.dto.FilmDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "film")
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public Film() {}
	
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

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getReleased() {
		return released;
	}

	public String getDirector() {
		return director;
	}

	public String getActors() {
		return actors;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public String getImdbID() {
		return imdbId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public void setImdbID(String imdbID) {
		this.imdbId = imdbID;
	}

	@Override
	public String toString() {
		return "FilmDao [id=" + id + ", title=" + title + ", year=" + year + ", released=" + released + ", director="
				+ director + ", actors=" + actors + ", imdbRating=" + imdbRating + ", imdbID=" + imdbId + "]";
	}
	
	public static Film of(FilmDto film) {
		
		Film filmDao = new Film(
				film.title(),
				film.year(),
				film.released(),
				film.director(),
				film.actors(),
				film.imdbRating(),
				film.imdbID()
				);
		
		return filmDao;
			
	}
	
}
