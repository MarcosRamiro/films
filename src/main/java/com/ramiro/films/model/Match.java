package com.ramiro.films.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ramiro.films.type.StatusMatchEnum;

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