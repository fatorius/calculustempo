package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    private static final int RATING_RANGE = 200;

    public Challenge pickARandomChallenge(int rating){
        int minRating = rating - RATING_RANGE;
        int maxRating = rating + RATING_RANGE;

        return challengeRepository.findRandomChallengeInRange(minRating, maxRating);
    }
}
