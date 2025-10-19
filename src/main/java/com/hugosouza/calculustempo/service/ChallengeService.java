package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.ChallengeRepository;
import com.hugosouza.calculustempo.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final IntegralService integralService;
    private final ChallengeRepository challengeRepository;

    private static final int USER_DEFAULT_RANGE = 30;

    public Challenge createAnnonymousChallenge(int rating){
        Integral integral = integralService.pickARandomIntegralAtRange(rating);

        Challenge newChallenge = new Challenge(
                IdGenerator.nextId(),
                integral.getId()
        );

        challengeRepository.save(newChallenge);

        return newChallenge;
    }

    public Challenge createChallengeForUser(User user){
        Integral integral = integralService.pickARandomIntegralAtRange(user.getRating(), USER_DEFAULT_RANGE);

        Challenge newChallenge = new Challenge(
                IdGenerator.nextId(),
                user.getId(),
                integral.getId()
        );

        challengeRepository.save(newChallenge);

        return newChallenge;
    }

    public Challenge getChallengeForUser(User user){
        if (this.userHasAnActiveChallenge(user)){
            return challengeRepository.getUserActiveChallenge(user.getId());
        }

        return this.createChallengeForUser(user);
    }

    private boolean userHasAnActiveChallenge(User user){
        return challengeRepository.findUserActiveChallenge(user.getId()).isPresent();
    }

}
