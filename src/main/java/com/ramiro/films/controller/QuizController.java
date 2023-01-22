package com.ramiro.films.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.films.domain.Quiz;
import com.ramiro.films.model.Match;
import com.ramiro.films.model.Move;
import com.ramiro.films.model.User;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {

	private final Quiz quiz;

	@PostMapping("/newMatch")
	public Move newMatch(User user) {
		
		Match match = quiz.newMatch(user);
		
		return quiz.newMove(match);
		
	}

}
