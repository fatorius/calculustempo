package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.repository.ChallengeRepository;
import com.hugosouza.calculustempo.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final IntegralService integralService;
    private final ChallengeRepository challengeRepository;

    public Challenge createAnnonymousChallenge(int rating){
        Integral integral = integralService.pickARandomIntegralAtRange(rating);

        Challenge newChallenge = new Challenge(
                IdGenerator.nextId(),
                integral.getId()
        );

        challengeRepository.save(newChallenge);

        return newChallenge;
    }
}
