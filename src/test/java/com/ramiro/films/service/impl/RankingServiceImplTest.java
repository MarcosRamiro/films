package com.ramiro.films.service.impl;

import com.ramiro.films.dto.RankingResponseDto;
import com.ramiro.films.model.Match;
import com.ramiro.films.model.User;
import com.ramiro.films.newmove.adapter.repo.MatchRepository;
import com.ramiro.films.newmove.entity.Move;
import com.ramiro.films.type.StatusMoveEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RankingServiceImplTest {

    @InjectMocks
    private RankingServiceImpl rankingServiceImpl;

    @Mock
    MatchRepository matchRepository;

    @Test
    public void deveCalcularRanking() {

        when(matchRepository.findAll()).thenReturn(listMatch());

        RankingResponseDto resultadoRanking = rankingServiceImpl.getRanking();

        assertEquals(2, resultadoRanking.getRanking().size());

        assertTrue(
                resultadoRanking.getRanking().stream().filter(
                                posicao -> posicao.getUsername().equalsIgnoreCase("maria"))
                        .allMatch(posicao -> posicao.getPosition() == 1
                                && posicao.getPoints() == 3
                        )
        );

        assertTrue(
                resultadoRanking.getRanking().stream().filter(
                                posicao -> posicao.getUsername().equalsIgnoreCase("joao"))
                        .allMatch(posicao -> posicao.getPosition() == 2
                                && posicao.getPoints() == 2
                        )
        );


    }

    private List<Match> listMatch() {

        List<Match> matches = new ArrayList<>();
        List<Move> movesMaria = new ArrayList<>();
        Match match1 = new Match();
        User user1 = new User();
        user1.setUsername("maria");
        match1.setUser(user1);
        Move mariaMove1 = new Move();
        mariaMove1.setStatus(StatusMoveEnum.OK);
        mariaMove1.setMatch(match1);
        Move mariaMove2 = new Move();
        mariaMove2.setStatus(StatusMoveEnum.OK);
        mariaMove2.setMatch(match1);
        Move mariaMove3 = new Move();
        mariaMove3.setStatus(StatusMoveEnum.OK);
        mariaMove3.setMatch(match1);
        Move mariaMove4 = new Move();
        mariaMove4.setStatus(StatusMoveEnum.NOK);
        mariaMove4.setMatch(match1);
        movesMaria.add(mariaMove1);
        movesMaria.add(mariaMove2);
        movesMaria.add(mariaMove3);
        movesMaria.add(mariaMove4);
        match1.setMoves(movesMaria);
        matches.add(match1);

        List<Move> movesJoao = new ArrayList<>();
        Match match2 = new Match();
        User user2 = new User();
        user2.setUsername("joao");
        match2.setUser(user2);
        Move joaoMove1 = new Move();
        joaoMove1.setStatus(StatusMoveEnum.OK);
        joaoMove1.setMatch(match2);
        Move joaoMove2 = new Move();
        joaoMove2.setStatus(StatusMoveEnum.OK);
        joaoMove2.setMatch(match2);
        Move joaoMove3 = new Move();
        joaoMove3.setStatus(StatusMoveEnum.NOK);
        joaoMove3.setMatch(match2);
        movesJoao.add(joaoMove1);
        movesJoao.add(joaoMove2);
        movesJoao.add(joaoMove3);
        match2.setMoves(movesJoao);
        matches.add(match2);
        return matches;

    }

}
