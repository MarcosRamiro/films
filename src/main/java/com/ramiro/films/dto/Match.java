package com.ramiro.films.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.ramiro.films.domain.User;
import com.ramiro.films.type.StatusMatchEnum;

public class Match {
	
	private final User user;
	private final String id;
	private final StatusMatchEnum status;
	private final List<Move> moves;
	
	public Match(User user) {
		this.user = user;
		this.id = UUID.randomUUID().toString();
		this.status = StatusMatchEnum.OPEN;
		this.moves = new ArrayList<Move>();
	}
	
	public Move newMove() {
		Move move = new Move(null, null);
		return move;
	}
	
	
	
	
	
	

	@Override
	public int hashCode() {
		return Objects.hash(id, moves, status, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		return Objects.equals(id, other.id) && Objects.equals(moves, other.moves) && status == other.status
				&& Objects.equals(user, other.user);
	}

}
