package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.repository.IntegralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegralService {

    private final IntegralRepository integralRepository;

    private static final int DEFAULT_RATING_RANGE = 50;

    public Integral pickARandomIntegralAtRange(int rating){
        int minRating = rating - DEFAULT_RATING_RANGE;
        int maxRating = rating + DEFAULT_RATING_RANGE;

        return integralRepository.findRandomIntegralInRange(minRating, maxRating);
    }

    public Integral pickARandomIntegralAtRange(int rating, int range){
        int minRating = rating - range;
        int maxRating = rating + range;

        return integralRepository.findRandomIntegralInRange(minRating, maxRating);
    }
}
