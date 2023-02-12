package com.ramiro.films.adapter.infra.repository;

import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.usecase.repository.AllUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AllUsers, JpaRepository<User, Long> {

    Optional<User> findTop1ByUsername(String username);

    @Override
    default Optional<User> findUserByUsername(String username) {
       return this.findTop1ByUsername(username);    }
}
