package com.ramiro.films.repository;

import java.util.Optional;
import java.util.UUID;

import com.ramiro.films.model.User;
import com.ramiro.films.type.StatusLoginEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ramiro.films.model.Login;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoginRepository extends JpaRepository<Login, Long> {

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query("update Login set DATE_TIME_END = now(), STATUS_LOGIN = ?1 where STATUS_LOGIN= ?2 and user_id=?3")
	void updateStatusAndDateTimeEndByStatusAndUser(String statusToBe, String statusAsIs, long user_id);

	Optional<Login> findTop1ByUserAndStatus(User user, StatusLoginEnum status);

}
