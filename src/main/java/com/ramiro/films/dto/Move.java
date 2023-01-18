package com.ramiro.films.dto;

import java.util.UUID;

import com.ramiro.films.type.StatusMoveEnum;

public class Move {
	
	private final String id;
	private final Film film1;
	private final Film film2;
	private StatusMoveEnum status;
	
	public Move(Film film1, Film film2) {
		this.id = UUID.randomUUID().toString();
		this.film1 = film1;
		this.film2 = film2;
		this.status = StatusMoveEnum.PENDING;
	}

	public Film getFilm1() {
		return film1;
	}

	public Film getFilm2() {
		return film2;
	}

	public StatusMoveEnum getStatus() {
		return status;
	}
	
}
