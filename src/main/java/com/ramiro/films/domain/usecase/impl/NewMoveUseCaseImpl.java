package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.MoveDto;
import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.usecase.*;
import com.ramiro.films.domain.usecase.repository.AllFilms;
import com.ramiro.films.domain.usecase.repository.AllMatches;
import com.ramiro.films.domain.usecase.repository.AllMoves;
import com.ramiro.films.domain.exceptions.MatchNotFoundException;
import com.ramiro.films.domain.entity.model.Film;
import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.entity.model.Move;
import com.ramiro.films.domain.type.StatusMoveEnum;
import com.ramiro.films.domain.usecase.repository.AllUsers;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

/**
 *  - Não há utilização de nenhuma classe/metadado do Spring
 *  - Indireção via Injeção de Depedência
 *  - Testável: possível 'mockar' todos as dependências
 *  - Independente de Banco de Dados
 *  - Independente de recursos externos
 */

@Slf4j
@Named
public class NewMoveUseCaseImpl implements NewMoveUseCase {

    // IoC
    private final AllMatches allMatches;
    private final AllMoves allMoves;
    private final AllFilms allFilms;
    private final AllUsers allUsers;
    private final UploadFilms uploadFilms;

    @Inject
    public NewMoveUseCaseImpl(AllMatches allMatches, AllMoves allMoves, AllFilms allFilms,
                              UploadFilms uploadFilms,
                              AllUsers allUsers) {
        this.allMatches = allMatches;
        this.allMoves = allMoves;
        this.allFilms = allFilms;
        this.uploadFilms = uploadFilms;
        this.allUsers = allUsers;
    }

    @Override
    public MoveDto newMove(UserDto userDto) throws MatchNotFoundException {

        User user = allUsers.findUserByUsername(userDto.getUsername()).get();

        Match match = getMatch(user);
        log.info("Quantidade de jogadas : " + match.getMoves().size());

        List<Move> moves = allMoves.getAllMovesFromUser(user);

        Optional<Move> movePending = moves.stream()
                            .filter(m -> m.getMatch().getId() == match.getId())
                            .filter(m -> m.getStatus().equals(StatusMoveEnum.PENDING))
                            .findFirst();
        if (movePending.isPresent())
            return prepareReturn(movePending.get());

        List<Film> films = getAllFilms();

        Optional<Move> moveOptionalFinal = generateMove(match, moves, films);

        while (moveOptionalFinal.isEmpty()) {
            uploadFilms.uploadFilms();
            films = getAllFilms();
            moveOptionalFinal = generateMove(match, moves, films);
        }

        Move move = moveOptionalFinal.get();
        allMoves.add(move);

        return prepareReturn(move);
    }

    private Optional<Move> generateMove(Match match, List<Move> moves, List<Film> films) {

        return films.stream()
                .map(film -> new Move(match, film, null))
                .flatMap(move ->
                        films.stream()
                                .filter(film -> film.getId() != move.getFilmA().getId())
                                .map(film -> new Move(move.getMatch(), move.getFilmA(), film)))
                .filter(move -> moves.stream().noneMatch(m ->
                        (m.getFilmA().getId() == move.getFilmA().getId() && m.getFilmB().getId() == move.getFilmB().getId())
                                ||
                                (m.getFilmA().getId() == move.getFilmB().getId() && m.getFilmB().getId() == move.getFilmA().getId())))
                .findFirst();
    }

    private Match getMatch(User user) throws MatchNotFoundException {
        Optional<Match> matchOptional = getMatchOptional(user);
        if (matchOptional.isEmpty())
            throw new MatchNotFoundException("Não há uma partida aberta. Inicie uma nova partida em 'Iniciar partida'.");
        return matchOptional.get();
    }

    private Optional<Match> getMatchOptional(User user) {
        return allMatches.getMathOpenByUser(user);
    }

    private List<Film> getAllFilms() {
        return allFilms.findAllFilms();
    }

    private MoveDto prepareReturn(Move move){
        return move.toDto();
    }


}
