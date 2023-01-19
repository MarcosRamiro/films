package com.ramiro.films.dao;

import com.ramiro.films.type.StatusMoveEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "move")
public class Move {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Film film1;
	
	@ManyToOne
	private Film film2;
	
	@Column(name = "status_move")
	@Enumerated(EnumType.STRING)
	private StatusMoveEnum status;
	
	@ManyToOne
	@JoinColumn(name = "match_id")
	private Match match;
	
	
	public Move(Match match, Film film1, Film film2) {
		this.match = match;
		this.film1 = film1;
		this.film2 =film2;
		this.status = StatusMoveEnum.PENDING;
	}


	public long getId() {
		return id;
	}


	public StatusMoveEnum getStatus() {
		return status;
	}


	public Match getMatch() {
		return match;
	}


	public void setId(long id) {
		this.id = id;
	}


	public void setStatus(StatusMoveEnum status) {
		this.status = status;
	}


	public void setMatch(Match match) {
		this.match = match;
	}
	
	public Film getFilm1() {
		return film1;
	}


	public Film getFilm2() {
		return film2;
	}


	public void setFilm1(Film film1) {
		this.film1 = film1;
	}


	public void setFilm2(Film film2) {
		this.film2 = film2;
	}


	@Override
	public String toString() {
		return "Move [id=" + id + ", status=" + status + ", match=" + match + "]";
	}

}
