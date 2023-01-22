package com.ramiro.films.domain.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ramiro.films.domain.FilmDataBase;
import com.ramiro.films.domain.Quiz;
import com.ramiro.films.model.Film;
import com.ramiro.films.model.Match;
import com.ramiro.films.model.Move;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.MatchRepository;
import com.ramiro.films.repository.MoveRepository;
import com.ramiro.films.repository.UserRepository;

@Component
@AllArgsConstructor
public class QuizImpl implements Quiz {

	private final FilmDataBase filmDataBase;
	private final MatchRepository matchRepository;
	private final MoveRepository moveRepository;
	private final UserRepository userRepository;

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
