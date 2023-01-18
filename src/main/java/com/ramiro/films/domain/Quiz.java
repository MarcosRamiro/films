package com.ramiro.films.domain;

import java.util.HashSet;
import java.util.Set;

import com.ramiro.films.dto.Match;
import com.ramiro.films.dto.Move;

public class Quiz {
	
	private final Set<Match> matches;
	private final FilmDataBase filmDataBase;
	
	public Quiz(FilmDataBase filmDataBase) {
		this.matches = new HashSet<>();
		this.filmDataBase = filmDataBase;
	}

	public Match startPlay(User user) {
		
		Match match = new Match(user);
		
		return match;
	}
	
	public Move newMove(Match match) {
		
		return null;
	}
	
	

}
