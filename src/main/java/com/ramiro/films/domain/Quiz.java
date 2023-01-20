package com.ramiro.films.domain;

import com.ramiro.films.dao.Match;
import com.ramiro.films.dao.Move;
import com.ramiro.films.dao.User;

public interface Quiz {
	
	Match newMatch(User user);
	
	Move newMove(Match match);

}
