package com.ramiro.films.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.dao.Login;
import com.ramiro.films.dao.User;

public interface LoginRepository extends JpaRepository<Login, UUID> {
	
	Optional<Login> findTop1ByUserAndDateTimeEndIsNull(User user);

}
