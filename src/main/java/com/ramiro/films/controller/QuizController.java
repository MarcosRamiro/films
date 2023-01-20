package com.ramiro.films.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramiro.films.dao.Match;
import com.ramiro.films.dao.Move;
import com.ramiro.films.dao.User;
import com.ramiro.films.domain.Quiz;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	private final Quiz quiz;
	
	public QuizController(Quiz quiz) {
		this.quiz = quiz;
	}

	@PostMapping("/newMatch")
	public Move newMatch(User user) {
		
		Match match = quiz.newMatch(user);
		
		return quiz.newMove(match);
		
	}

}
