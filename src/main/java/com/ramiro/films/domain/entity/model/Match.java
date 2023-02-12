package com.ramiro.films.domain.entity.model;

import com.ramiro.films.domain.type.StatusMatchEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "match")
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private long id;

    @ManyToOne
    private User user;

    @Column(name = "status_match")
    @Enumerated(EnumType.STRING)
    private StatusMatchEnum status;

    @OneToMany(mappedBy = "match")
    private List<Move> moves;

    @Column(name = "totalError")
    private int totalErro;

    public Match() {
    }

    public Match(User user, List<Move> moves) {
        this.user = user;
        this.status = StatusMatchEnum.OPEN;
        this.moves = moves;
        this.totalErro = 0;
    }

    public void addError() {
        this.totalErro++;
    }

}
