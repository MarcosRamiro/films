package com.ramiro.films.domain.impl;

import com.ramiro.films.adapter.dto.MoveFeedbackResponse;
import com.ramiro.films.adapter.dto.MoveRequest;
import com.ramiro.films.adapter.infra.repository.MatchRepository;
import com.ramiro.films.adapter.infra.repository.MoveRepository;
import com.ramiro.films.adapter.infra.repository.UserRepository;
import com.ramiro.films.domain.Quiz;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.entity.model.Film;
import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.entity.model.Move;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.exceptions.MatchNotFoundException;
import com.ramiro.films.domain.exceptions.MoveNotFoundException;
import com.ramiro.films.domain.type.FilmOptionEnum;
import com.ramiro.films.domain.type.StatusMatchEnum;
import com.ramiro.films.domain.type.StatusMoveEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuizImpl implements Quiz {

    private final MatchRepository matchRepository;
    private final MoveRepository moveRepository;
    private final UserRepository userRepository;

    private static int LIMIT_ERRORS = 3;
    public static String POSITIVE = "PARABÉNS!!!! Voce acertou. O Filme %s teve uma avaliação de %s, que é superior do que o outro filme.";
    public static String NEGATIVE = "ERROU! Você não acertou, mas não fique triste você ainda tem %d jogada(s).";
    public static String GAME_OVER = "GAME OVER! Compare seu resultado com os outros competidores.";

    @Override
    public Match newMatch(UserDto userDto) {
        User user = userRepository.findUserByUsername(userDto.getUsername()).get();
        Optional<Match> matchOptional = getMatchOptional(user);

        if (matchOptional.isPresent())
            return matchOptional.get();

        Match match = new Match(user, new ArrayList<>());
        match = matchRepository.save(match);

        return match;

    }


    private Match getMatch(User user) throws MatchNotFoundException {
        Optional<Match> matchOptional = getMatchOptional(user);
        if (matchOptional.isEmpty())
            throw new MatchNotFoundException("Não há uma partida aberta. Inicie uma nova partida em 'Iniciar partida'.");
        return matchOptional.get();
    }

    private Optional<Match> getMatchOptional(User user) {
        return matchRepository.findTop1ByUserAndStatus(user, StatusMatchEnum.OPEN);
    }

    @Override
    public MoveFeedbackResponse sendMove(UserDto userDto, MoveRequest moveRequest) throws MoveNotFoundException, MatchNotFoundException {

        User user = userRepository.findUserByUsername(userDto.getUsername()).get();

        Match match = getMatch(user);

        Optional<Move> moveOptionalPending = match.getMoves().stream().filter(m -> m.getStatus().equals(StatusMoveEnum.PENDING)).findFirst();
        if (moveOptionalPending.isEmpty())
            throw new MoveNotFoundException("Não há jogada pendente. Vá para 'Próxima jogada'.");

        Move move = moveOptionalPending.get();

        FilmOptionEnum correct = new BigDecimal(move.getFilmA().getImdbRating()).subtract(new BigDecimal(move.getFilmB().getImdbRating())).floatValue() > 0.00 ? FilmOptionEnum.A : FilmOptionEnum.B;

        if (FilmOptionEnum.A.equals(moveRequest.getFilmeComMaiorAvaliacao()) && FilmOptionEnum.A.equals(correct))
            move.setStatus(StatusMoveEnum.OK);
        else if (FilmOptionEnum.B.equals(moveRequest.getFilmeComMaiorAvaliacao()) && FilmOptionEnum.B.equals(correct))
            move.setStatus(StatusMoveEnum.OK);
        else {
            move.setStatus(StatusMoveEnum.NOK);
            match.addError();
        }

        if (match.getTotalErro() == LIMIT_ERRORS) {
            match.setStatus(StatusMatchEnum.CLOSED);
            matchRepository.save(match);
        }

        moveRepository.save(move);

        return prepareResult(moveRequest, match, move);
    }

    private MoveFeedbackResponse prepareResult(MoveRequest moveRequest, Match match, Move move) {
        String message;
        Film film = moveRequest.getFilmeComMaiorAvaliacao().equals(FilmOptionEnum.A) ? move.getFilmA() : move.getFilmB();

        if (move.getStatus().equals(StatusMoveEnum.OK))
            message = String.format(POSITIVE, moveRequest.getFilmeComMaiorAvaliacao().toString(), film.getImdbRating());
        else if (move.getStatus().equals(StatusMoveEnum.NOK) && match.getTotalErro() != LIMIT_ERRORS)
            message = String.format(NEGATIVE, 3 - match.getTotalErro());
        else
            message = GAME_OVER;

        return new MoveFeedbackResponse(message);
    }


}
