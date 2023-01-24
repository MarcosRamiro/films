package com.ramiro.films.model;

import com.ramiro.films.type.StatusMatchEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "match")
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

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public StatusMatchEnum getStatus() {
        return status;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(StatusMatchEnum status) {
        this.status = status;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public int getTotalErro() {
        return totalErro;
    }

    public void setTotalErro(int totalErro) {
        this.totalErro = totalErro;
    }

    public void addError() {
        this.totalErro++;
    }


}
