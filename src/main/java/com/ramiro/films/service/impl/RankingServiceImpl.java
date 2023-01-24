package com.ramiro.films.service.impl;

import com.ramiro.films.dto.PositionResponseDto;
import com.ramiro.films.dto.RankingResponseDto;
import com.ramiro.films.model.Match;
import com.ramiro.films.repository.MatchRepository;
import com.ramiro.films.service.RankingService;
import com.ramiro.films.type.StatusMoveEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final MatchRepository matchRepository;

    @Override
    public RankingResponseDto getRanking() {

        List<Match> matches = matchRepository.findAll();

        Map<String, Long> ranking = matches.stream()
                .flatMap(match -> match.getMoves().stream())
                .filter(move -> move.getStatus().equals(StatusMoveEnum.OK))
                .collect(Collectors.groupingBy(move -> move.getMatch().getUser().getUsername(), Collectors.counting()));

        Map<String, Long> finalResult = new LinkedHashMap<>();

        ranking.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalResult.put(e.getKey(), e.getValue()));

        int position = 1;
        List<PositionResponseDto> positions = new ArrayList<>();
        for (Map.Entry<String, Long> entry : finalResult.entrySet()) {
            positions.add(new PositionResponseDto(position, entry.getKey(), Integer.valueOf(entry.getValue().toString())));
            position++;
        }

        RankingResponseDto responseDto = new RankingResponseDto();
        responseDto.setRanking(positions);

        return responseDto;
    }
}
