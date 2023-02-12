package com.ramiro.films.domain.entity.model;

import com.ramiro.films.domain.entity.dto.MoveDto;
import com.ramiro.films.domain.type.StatusMoveEnum;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "move")
@Data
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Film filmA;

    @ManyToOne
    private Film filmB;

    @Column(name = "status_move")
    @Enumerated(EnumType.STRING)
    private StatusMoveEnum status;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public Move() {
    }

    public Move(Match match, Film filmA, Film filmB) {
        this.match = match;
        this.filmA = filmA;
        this.filmB = filmB;
        this.status = StatusMoveEnum.PENDING;
    }

    public MoveDto toDto() {
        MoveDto newMove = new MoveDto();
        newMove.setFilmA(this.filmA.toDto());
        newMove.setFilmB(this.filmB.toDto());
        return newMove;
    }

}
