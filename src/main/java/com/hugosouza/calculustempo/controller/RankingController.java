package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.dto.RankingResponse;
import com.hugosouza.calculustempo.interfaces.RankingProjection;
import com.hugosouza.calculustempo.interfaces.ResponseData;
import com.hugosouza.calculustempo.interfaces.SuccessResponse;
import com.hugosouza.calculustempo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final UserRepository userRepository;

    @GetMapping
    @Cacheable(value = "ranking")
    public ResponseData<RankingResponse[]> getRanking(){
        List<RankingProjection> results = userRepository.getRanking();

        RankingResponse[] response = results.stream()
                .map(r -> new RankingResponse(r.getUsername(), r.getRating()))
                .toArray(RankingResponse[]::new);

        return new SuccessResponse<>(response);

    }
}
