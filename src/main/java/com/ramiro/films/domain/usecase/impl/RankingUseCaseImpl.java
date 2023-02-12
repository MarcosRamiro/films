package com.ramiro.films.domain.usecase.impl;

import com.ramiro.films.domain.entity.dto.PositionDto;
import com.ramiro.films.domain.entity.dto.RankingDto;
import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.domain.type.StatusMoveEnum;
import com.ramiro.films.domain.usecase.RankingUseCase;
import com.ramiro.films.domain.usecase.repository.AllMatches;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequiredArgsConstructor
public class RankingUseCaseImpl implements RankingUseCase {

    private final AllMatches allMatches;

    @Override
    public RankingDto getRanking() {

        List<Match> matches = allMatches.getAll();

        Map<String, Long> ranking = matches.stream()
                .flatMap(match -> match.getMoves().stream())
                .filter(move -> move.getStatus().equals(StatusMoveEnum.OK))
                .collect(Collectors.groupingBy(move -> move.getMatch().getUser().getUsername(), Collectors.counting()));

        Map<String, Long> finalResult = new LinkedHashMap<>();

        ranking.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalResult.put(e.getKey(), e.getValue()));

        int position = 1;
        List<PositionDto> positions = new ArrayList<>();
        for (Map.Entry<String, Long> entry : finalResult.entrySet()) {
            positions.add(new PositionDto(position, entry.getKey(), Integer.valueOf(entry.getValue().toString())));
            position++;
        }

        RankingDto rankingDto = new RankingDto();
        rankingDto.setPositions(positions);

        return rankingDto;
    }
}
