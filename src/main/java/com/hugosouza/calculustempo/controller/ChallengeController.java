package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.dto.ChallengeAnswerRequest;
import com.hugosouza.calculustempo.dto.ChallengeAnswerResponse;
import com.hugosouza.calculustempo.dto.GetAChallengeResponse;
import com.hugosouza.calculustempo.interfaces.ErrorResponse;
import com.hugosouza.calculustempo.interfaces.ResponseData;
import com.hugosouza.calculustempo.interfaces.SuccessResponse;
import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.IntegralRepository;
import com.hugosouza.calculustempo.repository.UserRepository;
import com.hugosouza.calculustempo.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;
    private final IntegralRepository integralRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseData<GetAChallengeResponse> getChallenge(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "level", defaultValue = "1800") int level
    ) {
        Challenge createdChallenge;

        if (userDetails == null) {
            createdChallenge = challengeService.createAnnonymousChallenge(level);
        } else {
            User user = userRepository.findByUsername(userDetails.getUsername());
            createdChallenge = challengeService.getChallengeForUser(user);
        }

        Integral chosenIntegral = integralRepository.getReferenceById(createdChallenge.getIntegral_id());
        return new SuccessResponse<>(new GetAChallengeResponse(createdChallenge.getId(), chosenIntegral.getExpression_latex()));
    }

    @PostMapping("answer/{challenge_id}")
    public ResponseData<ChallengeAnswerResponse> answerChallenge(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("challenge_id") Long challengeId,
            @RequestBody ChallengeAnswerRequest challengeAnswerRequest
    ) {
        Optional<Challenge> challengeRequest = challengeService.findById(challengeId);

        if (challengeRequest.isEmpty()){
            return new ErrorResponse<>("Challenge not found");
        }

        Challenge challenge = challengeRequest.get();

        if (challenge.getResult_success() != null){
            return new ErrorResponse<>("Unauthorized");
        }

        Long challengeUserId = challenge.getUser_id();

        if (challengeUserId != null) {
            if (userDetails == null) {
                return new ErrorResponse<>("Unauthorized");
            }

            User user = userRepository.findByUsername(userDetails.getUsername());

            if (!challengeUserId.equals(user.getId())){
                return new ErrorResponse<>("Unauthorized");
            }
        }

        ChallengeAnswerResponse response = challengeService.answerChallenge(challenge, challengeAnswerRequest.getAnswer());

        return new SuccessResponse<>(response);
    }
}
