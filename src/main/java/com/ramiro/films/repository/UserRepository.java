package com.ramiro.films.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	Optional<User> findTop1ByUsername(String username);

}
