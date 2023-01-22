package com.ramiro.films.model;

import com.ramiro.films.type.StatusLoginEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "login")
@NoArgsConstructor
@Data
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private long id;

    @ManyToOne
    private User user;

    @Column(name = "dateTimeStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTimeStart = Calendar.getInstance();

    @Column(name = "dateTimeEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateTimeEnd;

    @Column(name = "status_login")
    @Enumerated(EnumType.STRING)
    private StatusLoginEnum status = StatusLoginEnum.OPEN;

    @Column(name = "token")
    private String token;

    public Login(User user, String token){
        this.user = user;
        this.token = token;
    }

}