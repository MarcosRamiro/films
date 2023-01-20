package com.ramiro.films.dao;

import java.util.List;

import com.ramiro.films.type.StatusMatchEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "match")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "match_id")
	private long id;

	@ManyToOne
	private User user;

	@Column(name = "status_match")
	@Enumerated(EnumType.STRING)
	private StatusMatchEnum status;

	@OneToMany(mappedBy = "match")
	private List<Move> moves;

	public Match() {}
	
	public Match(User user, List<Move> moves) {
		this.user = user;
		this.status = StatusMatchEnum.OPENED;
		this.moves = moves;
	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public StatusMatchEnum getStatus() {
		return status;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", user=" + user + ", status=" + status + ", moves=" + moves + "]";
	}

}
