package com.ramiro.films.service.impl;

import com.ramiro.films.adapter.dto.PositionResponse;
import com.ramiro.films.adapter.dto.RankingResponse;
import com.ramiro.films.domain.entity.model.Match;
import com.ramiro.films.adapter.infra.repository.MatchRepository;
import com.ramiro.films.service.RankingService;
import com.ramiro.films.domain.type.StatusMoveEnum;
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
    public RankingResponse getRanking() {

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
        List<PositionResponse> positions = new ArrayList<>();
        for (Map.Entry<String, Long> entry : finalResult.entrySet()) {
            positions.add(new PositionResponse(position, entry.getKey(), Integer.valueOf(entry.getValue().toString())));
            position++;
        }

        RankingResponse responseDto = new RankingResponse();
        responseDto.setRanking(positions);

        return responseDto;
    }
}
