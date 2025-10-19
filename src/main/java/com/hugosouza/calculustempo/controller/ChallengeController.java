package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.dto.GetAChallengeResponse;
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
}
