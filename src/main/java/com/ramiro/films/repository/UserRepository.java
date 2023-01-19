package com.ramiro.films.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.dao.User;

public interface UserRepository extends JpaRepository<User, Long>  {

}
