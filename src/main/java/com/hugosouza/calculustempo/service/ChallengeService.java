package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.repository.IntegralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final IntegralRepository integralRepository;

    private static final int RATING_RANGE = 200;

    public Integral pickARandomIntegral(int rating){
        int minRating = rating - RATING_RANGE;
        int maxRating = rating + RATING_RANGE;

        return integralRepository.findRandomIntegralInRange(minRating, maxRating);
    }
}
