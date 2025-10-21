package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.IntegralRepository;
import com.hugosouza.glicko2.Glicko2;
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

    public int updateIntegralRatings(User user, Integral integral, boolean correct){
        double sIntegral = correct ? 0.0 : 1.0;

        double[] integralUpdate = Glicko2.update(
                integral.getRating(),
                integral.getRating_deviation(),
                integral.getVolatility(),
                user.getRating(),
                user.getRating_deviation(),
                sIntegral
        );

        integral.setRating((int) integralUpdate[0]);
        integral.setRating_deviation(integralUpdate[1]);
        integral.setVolatility(integralUpdate[2]);

        return (int) integralUpdate[0];
    }
}
