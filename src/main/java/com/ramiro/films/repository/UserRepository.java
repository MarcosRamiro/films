package com.ramiro.films.repository;

import com.ramiro.films.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findTop1ByUsername(String username);

}
