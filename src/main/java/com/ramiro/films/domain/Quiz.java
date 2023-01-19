package com.ramiro.films.domain;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ramiro.films.dao.Film;
import com.ramiro.films.dao.Match;
import com.ramiro.films.dao.Move;
import com.ramiro.films.dao.User;
import com.ramiro.films.repository.MatchRepository;
import com.ramiro.films.repository.MoveRepository;
import com.ramiro.films.repository.UserRepository;

@Component
public class Quiz {

	private final FilmDataBase filmDataBase;
	private final MatchRepository matchRepository;
	private final MoveRepository moveRepository;
	private final UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(Quiz.class);

	public Quiz(FilmDataBase filmDataBase, MatchRepository matchRepository, UserRepository userRepository,
			MoveRepository moveRepository) {
		this.filmDataBase = filmDataBase;
		this.matchRepository = matchRepository;
		this.userRepository = userRepository;
		this.moveRepository = moveRepository;
		
		logger.info(newMatch(new User("maria", "ds")).toString());
		
	}

	public Move newMatch(User user) {
		
		Match match = matchRepository.save(new Match(userRepository.save(user), new ArrayList<>()));
		List<Film> films = filmDataBase.getTwoFilms();
		Film film1 = films.remove(0);
		Film film2 = films.remove(0);
		Move move = moveRepository.save(new Move(match,film1, film2));
		return move;
	}

}
