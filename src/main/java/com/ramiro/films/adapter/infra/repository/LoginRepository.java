package com.ramiro.films.adapter.infra.repository;

import com.ramiro.films.domain.entity.model.Login;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.type.StatusLoginEnum;
import com.ramiro.films.domain.usecase.repository.AllLogins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LoginRepository extends AllLogins, JpaRepository<Login, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("update Login set DATE_TIME_END = now(), STATUS_LOGIN = ?1 where STATUS_LOGIN= ?2 and user_id=?3")
    void updateStatusAndDateTimeEndByStatusAndUser(String statusToBe, String statusAsIs, long user_id);

    @Query("select lo from Login lo, User us where lo.user = us and us.username = ?1 and lo.status = ?2")
    Optional<Login> findTop1ByUserAndStatus(String username, StatusLoginEnum status);

    @Override
    default Optional<Login> findOpenLogin(String username){
        return this.findTop1ByUserAndStatus(username, StatusLoginEnum.OPEN);
    }

    @Override
    default void saveLogin(Login login){
        this.save(login);
    }

    @Override
    default void updateLogin(StatusLoginEnum statusToBe, StatusLoginEnum statusAsIs, long user_id){
        this.updateStatusAndDateTimeEndByStatusAndUser(statusToBe.toString(), statusAsIs.toString(), user_id);
    }
}
