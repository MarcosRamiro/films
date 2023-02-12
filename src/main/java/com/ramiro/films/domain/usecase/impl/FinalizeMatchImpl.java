package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.UserDto;
import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.entity.model.User;
import com.ramiro.films.domain.type.StatusMatchEnum;
import com.ramiro.films.domain.usecase.FinalizeMatch;
import com.ramiro.films.domain.usecase.repository.AllMatches;
import com.ramiro.films.domain.usecase.repository.AllUsers;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.Optional;

@Named
@RequiredArgsConstructor
public class FinalizeMatchImpl implements FinalizeMatch {

    private final AllUsers allUsers;
    private final AllMatches allMatches;

    @Override
    public void finalizeIfPresent(UserDto userDto) {

        User user = allUsers.findUserByUsername(userDto.getUsername()).get();

        allMatches.getMathOpenByUser(user)
                .ifPresent(match -> {
                            match.setStatus(StatusMatchEnum.CLOSED);
                            allMatches.saveMatch(match);
        });

    }


}
