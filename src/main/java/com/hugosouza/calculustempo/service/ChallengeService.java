package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.dto.ChallengeAnswerResponse;
import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.ChallengeRepository;
import com.hugosouza.calculustempo.repository.IntegralRepository;
import com.hugosouza.calculustempo.repository.UserRepository;
import com.hugosouza.calculustempo.util.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final IntegralService integralService;
    private final ChallengeRepository challengeRepository;
    private final IntegralRepository integralRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private static final int USER_DEFAULT_RANGE = 80;

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

        if (integral == null) {
            integral = integralRepository.findClosestIntegralFallback(user.getRating());
        }

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

    @Transactional
    public ChallengeAnswerResponse answerChallenge(Challenge challenge, String answer){
        Challenge managedChallenge = challengeRepository.getReferenceById(challenge.getId());

        Integral challengeIntegral = integralRepository.getReferenceById(managedChallenge.getIntegral_id());

        boolean correct = challengeIntegral.getSolution_latex().equals(answer);
        managedChallenge.setResult_success(correct);

        int userNewRating = 0;
        if (managedChallenge.getUser_id() != null){
            User user = userRepository.getReferenceById(managedChallenge.getUser_id());

            userNewRating = userService.updateUserRatings(user, challengeIntegral, correct);
            int integralNewRating = integralService.updateIntegralRatings(user, challengeIntegral, correct);

            this.updateRatingsOnChallenge(managedChallenge, userNewRating, integralNewRating);
        }

        return new ChallengeAnswerResponse(correct, challengeIntegral, userNewRating);
    }

    private void updateRatingsOnChallenge(Challenge challenge, int userRating, int integralRating){
        challenge.setNew_integral_rating(integralRating);
        challenge.setNew_user_rating(userRating);
    }

    private boolean userHasAnActiveChallenge(User user){
        return challengeRepository.findUserActiveChallenge(user.getId()).isPresent();
    }
}
