/*package com.ramiro.films.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.model.Login;
import com.ramiro.films.model.User;

public interface LoginRepository extends JpaRepository<Login, UUID> {
	
	Optional<Login> findTop1ByUserAndDateTimeEndIsNull(User user);

}
*/