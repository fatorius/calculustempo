package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.dto.ChallengeAnswerResponse;
import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.ChallengeRepository;
import com.hugosouza.calculustempo.repository.IntegralRepository;
import com.hugosouza.calculustempo.repository.UserRepository;
import com.hugosouza.calculustempo.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final IntegralService integralService;
    private final ChallengeRepository challengeRepository;
    private final IntegralRepository integralRepository;

    private static final int USER_DEFAULT_RANGE = 30;
    private final UserRepository userRepository;

    public Optional<Challenge> findById(long challengeId){
        return challengeRepository.findById(challengeId);
    }

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

    public ChallengeAnswerResponse answerChallenge(Challenge challenge, String answer){
        Integral challengeIntegral = integralRepository.getReferenceById(challenge.getIntegral_id());

        boolean correct = challengeIntegral.getSolution_latex().equals(answer);

        if (challenge.getUser_id() != null){
            User user = userRepository.getReferenceById(challenge.getUser_id());
            this.updateRatings(user, challengeIntegral);
        }

        return new ChallengeAnswerResponse(correct, challengeIntegral);
    }

    private void updateRatings(User user, Integral integral){

    }

    private boolean userHasAnActiveChallenge(User user){
        return challengeRepository.findUserActiveChallenge(user.getId()).isPresent();
    }
}
