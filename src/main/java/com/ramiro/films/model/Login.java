/*package com.ramiro.films.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "login")
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@ManyToOne
	private User user;
	
	@Column(name = "dateTimeStart")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateTimeStart;
	
	@Column(name = "dateTimeEnd")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateTimeEnd;
	
	public Login() {}
	
	public Login(User user) {
		this.user = user;
		this.dateTimeStart = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public LocalDateTime getDateTimeStart() {
		return dateTimeStart;
	}

	public LocalDateTime getDateTimeEnd() {
		return dateTimeEnd;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDateTimeStart(LocalDateTime dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}

	public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", user=" + user + ", dateTimeStart=" + dateTimeStart + ", dateTimeEnd="
				+ dateTimeEnd + "]";
	}

}
*/