package com.ramiro.films.domain.impl;

import com.ramiro.films.domain.FilmDataBase;
import com.ramiro.films.dto.MoveFeedbackResponseDto;
import com.ramiro.films.dto.MoveRequestDto;
import com.ramiro.films.handler.exceptions.MatchNotFoundException;
import com.ramiro.films.handler.exceptions.MoveNotFoundException;
import com.ramiro.films.model.Film;
import com.ramiro.films.model.Match;
import com.ramiro.films.model.Move;
import com.ramiro.films.model.User;
import com.ramiro.films.repository.FilmRepository;
import com.ramiro.films.repository.MatchRepository;
import com.ramiro.films.repository.MoveRepository;
import com.ramiro.films.type.FilmOptionEnum;
import com.ramiro.films.type.StatusMatchEnum;
import com.ramiro.films.type.StatusMoveEnum;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class QuizImplTest {

    @InjectMocks
    private QuizImpl quiz;

    @Mock
    private FilmDataBase filmDataBase;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MoveRepository moveRepository;

    @Test
    public void deveFinalizarPartida() {

        Optional<Match> dummyMatchPresent = getDummyOptionalMatch();

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(dummyMatchPresent);
        quiz.finalizeMatch(Mockito.mock(User.class));

        verify(matchRepository, times(1)).findTop1ByUserAndStatus(any(), any());
        verify(matchRepository, times(1)).save(dummyMatchPresent.get());

    }

    @Test
    public void naoDevePermitirEnviarJogadaQuandoNaoHaPartidaAberta() {

        Exception exception = assertThrows(MatchNotFoundException.class, () -> {
            quiz.sendMove(Mockito.mock(User.class), Mockito.mock(MoveRequestDto.class));
        });

        assertEquals("Não há uma partida aberta. Inicie uma nova partida em 'Iniciar partida'.", exception.getMessage());

    }

    @Test
    public void naoDevePermitirEnviarJogadaQuandoNaoHaJogadaPendente() {

        Optional<Match> dummyMatchPresent = getDummyOptionalMatch();
        Match match = dummyMatchPresent.get();
        match.setMoves(new ArrayList<>());

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(dummyMatchPresent);

        Exception exception = assertThrows(MoveNotFoundException.class, () -> {
            quiz.sendMove(Mockito.mock(User.class), Mockito.mock(MoveRequestDto.class));
        });

        assertEquals("Não há jogada pendente. Vá para 'Próxima jogada'.", exception.getMessage());

    }

    @ParameterizedTest
    @CsvSource({"true,B,5.0,6.0", "true,A,7.0,6.0", "false,A,7.0,8.0"})
    public void deveIdentificarSeUsuarioAcertouOuErrou(Boolean cenarioAcertou, String film, String notaFilmA, String notaFilmB) {

        Match match = getMatchComMovePendente();
        Move move = match.getMoves().stream().findFirst().get();
        Film filmA = move.getFilmA();
        filmA.setImdbRating(notaFilmA);
        Film filmB = move.getFilmB();
        filmB.setImdbRating(notaFilmB);
        MoveRequestDto moveRequestDto = new MoveRequestDto();
        moveRequestDto.setFilmeComMaiorAvaliacao(FilmOptionEnum.valueOf(film));
        String notaVencedor = new BigDecimal(move.getFilmA().getImdbRating()).subtract(new BigDecimal(move.getFilmB().getImdbRating())).floatValue() > 0.00 ? notaFilmA : notaFilmB;

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.of(match));

        MoveFeedbackResponseDto moveFeedbackResponseDto = quiz.sendMove(mock(User.class), moveRequestDto);

        assertEquals(cenarioAcertou ?
                        String.format("PARABÉNS!!!! Voce acertou. O Filme %s teve uma avaliação de %s, que é superior do que o outro filme.", film, notaVencedor)
                        : "ERROU! Você não acertou, mas não fique triste você ainda tem 2 jogadas.",
                moveFeedbackResponseDto.getResult());

    }

    @Test
    public void deveFinalizarPartidaQuandoUsuarioErrarTresVezes() {

        Match match = getMatchComMovePendente();
        Move move = match.getMoves().stream().findFirst().get();
        Film filmA = move.getFilmA();
        filmA.setImdbRating("1.0");
        Film filmB = move.getFilmB();
        filmB.setImdbRating("2.0");
        MoveRequestDto moveRequestDtoFilmA = new MoveRequestDto();
        moveRequestDtoFilmA.setFilmeComMaiorAvaliacao(FilmOptionEnum.A);
        MoveRequestDto moveRequestDtoFilmB = new MoveRequestDto();
        moveRequestDtoFilmB.setFilmeComMaiorAvaliacao(FilmOptionEnum.B);

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.of(match));

        MoveFeedbackResponseDto moveFeedbackResponseDto = quiz.sendMove(mock(User.class), moveRequestDtoFilmA);

        move = getMovePendente();
        move.getFilmA().setImdbRating("1.0");
        move.getFilmB().setImdbRating("2.0");
        match.getMoves().add(move);
        moveFeedbackResponseDto = quiz.sendMove(mock(User.class), moveRequestDtoFilmB);

        move = getMovePendente();
        move.getFilmA().setImdbRating("1.0");
        move.getFilmB().setImdbRating("2.0");
        match.getMoves().add(move);
        moveFeedbackResponseDto = quiz.sendMove(mock(User.class), moveRequestDtoFilmA);

        move = getMovePendente();
        move.getFilmA().setImdbRating("1.0");
        move.getFilmB().setImdbRating("2.0");
        match.getMoves().add(move);
        moveFeedbackResponseDto = quiz.sendMove(mock(User.class), moveRequestDtoFilmA);

        assertEquals("GAME OVER! Compare seu resultado com os outros competidores.", moveFeedbackResponseDto.getResult());
        assertEquals(StatusMatchEnum.CLOSED, match.getStatus());
        verify(matchRepository, times(1)).save(match);
        verify(moveRepository, times(4)).save(any());

    }

    @Test
    public void deveCriarUmaNovaPartida() {

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.empty());
        Match matchResultado = quiz.newMatch(Mockito.mock(User.class));

        verify(matchRepository, times(1)).save(any());

    }

    @Test
    public void naoDeveCriarPartidaDuplicada() {

        Match match = getMatchComMovePendente();
        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.of(match));

        Match matchResultado = quiz.newMatch(Mockito.mock(User.class));

        assertEquals(match, matchResultado);
        verify(matchRepository, times(0)).save(match);

    }

    @Test
    public void naoDevePermitirGerarMaisDeUmaJogadaPendente() {

        Match match = getMatchComMovePendente();

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.of(match));

        Move moveResultado = quiz.newMove(Mockito.mock(User.class));

        assertEquals(match.getMoves().stream().findFirst().get(), moveResultado);

    }

    @Test
    public void deveGerarParDeFilmesCorretamenteNaPrimeiraJogada() {

        Match matchSemJogada = getDummyOptionalMatch().get();
        List<Film> filmsDummy = getAllFilmsDummy();

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.of(matchSemJogada));
        when(filmRepository.findAll()).thenReturn(filmsDummy);

        Move moveResultado = quiz.newMove(Mockito.mock(User.class));

        assertTrue(filmsDummy.stream().anyMatch(film -> film.equals(moveResultado.getFilmA())));
        assertTrue(filmsDummy.stream().anyMatch(film -> film.equals(moveResultado.getFilmB())));
        verify(filmRepository, times(1)).findAll();
        verify(filmDataBase, times(0)).uploadFilms();
    }


    @ParameterizedTest
    @CsvSource({"2,2,0", "3,4,1"})
    public void deveGerarParDeFilmesNaoRepetidos(int chamadasNewMove, int chamadasGetlAllFilms, int chamadasUploadFilms) {

        Match matchSemJogada = getDummyOptionalMatch().get();
        List<Film> filmsDummy = getAllFilmsDummy();
        List<Film> filmsDummySegundaChamada = getAllFilmsDummy2();
        filmsDummySegundaChamada.addAll(filmsDummy);
        Move move = new Move(matchSemJogada, null, null);
        move.setId(1L);
        filmsDummy.stream().limit(2).forEach(
                film -> {
                    if (move.getFilmA() == null)
                        move.setFilmA(film);
                    else
                        move.setFilmB(film);
                }
        );
        move.setStatus(StatusMoveEnum.OK);
        List<Move> jogadas = new ArrayList<>();
        jogadas.add(move);
        matchSemJogada.setMoves(jogadas);

        when(matchRepository.findTop1ByUserAndStatus(any(), any())).thenReturn(Optional.of(matchSemJogada));
        when(filmRepository.findAll()).thenReturn(filmsDummy);


        Move moveResultado;
        long id = 0L;
        for (int i = 1; i < chamadasNewMove; i++) {
            moveResultado = quiz.newMove(Mockito.mock(User.class));
            moveResultado.setStatus(StatusMoveEnum.OK);
            id = i + 1;
            moveResultado.setId(id);
            matchSemJogada.getMoves().add(moveResultado);
        }

        //reset(filmRepository);
        when(filmRepository.findAll()).thenReturn(filmsDummy, filmsDummySegundaChamada);
        moveResultado = quiz.newMove(Mockito.mock(User.class));
        id++;
        moveResultado.setId(id);
        matchSemJogada.getMoves().add(moveResultado);

        boolean resultatoEsperadoTrue = matchSemJogada.getMoves().stream()
                .filter(moveOk -> matchSemJogada.getMoves().stream().filter(m -> m.getId() != moveOk.getId())
                        .allMatch(
                                movePending -> (movePending.getFilmA().getId() == moveOk.getFilmA().getId()
                                        && movePending.getFilmB().getId() == moveOk.getFilmB().getId()
                                )
                                        ||
                                        (
                                                movePending.getFilmA().getId() == moveOk.getFilmB().getId()
                                                        && movePending.getFilmB().getId() == moveOk.getFilmA().getId()
                                        )
                        )).findFirst().isEmpty();

        assertTrue(resultatoEsperadoTrue);

        matchSemJogada.getMoves().stream().forEach(m -> System.out.println(
                m.getStatus() + " -> move.getFilmA(): " + m.getFilmA().getId() + " | " + "move.getFilmB(): " + m.getFilmB().getId()
        ));

        verify(filmRepository, times(chamadasGetlAllFilms)).findAll();
        verify(filmDataBase, times(chamadasUploadFilms)).uploadFilms();
    }

    private List<Film> getAllFilmsDummy() {
        List<Film> films = new ArrayList<>();
        Film film = new Film("A dama e o vagabundo", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "9.00", "1");
        film.setId(Long.valueOf(film.getImdbId()));
        films.add(film);
        film = new Film("A viagem de barco", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "6.00", "2");
        film.setId(Long.valueOf(film.getImdbId()));
        films.add(film);
        film = new Film("o mar azul", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "8.00", "3");
        film.setId(Long.valueOf(film.getImdbId()));
        films.add(film);
        return films;
    }

    private List<Film> getAllFilmsDummy2() {
        List<Film> films = new ArrayList<>();
        Film film = new Film("Na estrada", "2000", "2000", "Arthur Silva"
                , "Alan Silva, Rodrigo alberto", "9.00", "4");
        film.setId(Long.valueOf(film.getImdbId()));
        films.add(film);
        return films;
    }

    private Match getMatchComMovePendente() {
        Optional<Match> dummyMatchPresent = getDummyOptionalMatch();
        Match match = dummyMatchPresent.get();
        List<Move> moves = new ArrayList<>();
        Film filmA = new Film();
        Film filmB = new Film();
        Move move = new Move(match, filmA, filmB);
        move.setStatus(StatusMoveEnum.PENDING);
        moves.add(move);
        match.setMoves(moves);
        return match;
    }

    private Move getMovePendente() {
        Film filmA = new Film();
        Film filmB = new Film();
        Move move = new Move(null, filmA, filmB);
        move.setStatus(StatusMoveEnum.PENDING);
        return move;
    }

    private Optional<Match> getDummyOptionalMatch() {
        return Optional.of(new Match(Mockito.mock(User.class), new ArrayList<>()));
    }

}
