package com.ramiro.films.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ramiro.films.type.StatusMoveEnum;

@Entity
@Table(name = "move")
public class Move {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Film filmA;
	
	@ManyToOne
	private Film filmB;
	
	@Column(name = "status_move")
	@Enumerated(EnumType.STRING)
	private StatusMoveEnum status;
	
	@ManyToOne
	@JoinColumn(name = "match_id")
	private Match match;
	
	public Move() {}
	
	public Move(Match match, Film filmA, Film filmB) {
		this.match = match;
		this.filmA = filmA;
		this.filmB =filmB;
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
	
	public Film getFilmA() {
		return filmA;
	}

	public Film getFilmB() {
		return filmB;
	}

	public void setFilmA(Film filmA) {
		this.filmA = filmA;
	}

	public void setFilmB(Film filmB) {
		this.filmB = filmB;
	}

	@Override
	public String toString() {
		return "Move [id=" + id + ", status=" + status + ", match=" + match + "]";
	}

}
