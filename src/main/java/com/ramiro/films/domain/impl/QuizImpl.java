package com.ramiro.films.domain.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ramiro.films.dao.Film;
import com.ramiro.films.dao.Match;
import com.ramiro.films.dao.Move;
import com.ramiro.films.dao.User;
import com.ramiro.films.domain.FilmDataBase;
import com.ramiro.films.domain.Quiz;
import com.ramiro.films.repository.MatchRepository;
import com.ramiro.films.repository.MoveRepository;
import com.ramiro.films.repository.UserRepository;

@Component
public class QuizImpl implements Quiz {

	private final FilmDataBase filmDataBase;
	private final MatchRepository matchRepository;
	private final MoveRepository moveRepository;
	private final UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(QuizImpl.class);

	public QuizImpl(FilmDataBase filmDataBase,
					MatchRepository matchRepository,
					UserRepository userRepository,
			MoveRepository moveRepository) {
		this.filmDataBase = filmDataBase;
		this.matchRepository = matchRepository;
		this.userRepository = userRepository;
		this.moveRepository = moveRepository;
	}

	@Override
	public Match newMatch(User user) {

		return matchRepository.save(new Match(userRepository.save(user), new ArrayList<>()));

	}

	@Override
	public Move newMove(Match match) {

		List<Film> films = filmDataBase.getTwoFilms();
		Film film1 = films.remove(0);
		Film film2 = films.remove(0);
		Move move = moveRepository.save(new Move(match, film1, film2));
		return move;

	}

}
