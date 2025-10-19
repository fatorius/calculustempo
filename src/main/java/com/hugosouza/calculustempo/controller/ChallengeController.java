package com.hugosouza.calculustempo.controller;

import com.hugosouza.calculustempo.dto.GetAChallengeResponse;
import com.hugosouza.calculustempo.interfaces.ResponseData;
import com.hugosouza.calculustempo.interfaces.SuccessResponse;
import com.hugosouza.calculustempo.model.Challenge;
import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.repository.IntegralRepository;
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

    @GetMapping
    public ResponseData<GetAChallengeResponse> getChallenge(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "level", defaultValue = "1800") int level
    ) {
        if (userDetails == null) {
            Challenge createdChallenge = challengeService.createAnnonymousChallenge(level);
            Integral chosenIntegral = integralRepository.getReferenceById(createdChallenge.getIntegral_id());

            return new SuccessResponse<>(new GetAChallengeResponse(createdChallenge.getId(), chosenIntegral.getExpression_latex()));
        }


        Challenge createdChallenge = challengeService.createAnnonymousChallenge(level);
        Integral chosenIntegral = integralRepository.getReferenceById(createdChallenge.getId());

        return new SuccessResponse<>(new GetAChallengeResponse(createdChallenge.getId(), chosenIntegral.getExpression_latex()));
    }
}
