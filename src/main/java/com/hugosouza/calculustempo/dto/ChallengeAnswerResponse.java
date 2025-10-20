package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugosouza.calculustempo.model.Integral;

public class ChallengeAnswerResponse {
    @JsonProperty
    private final boolean correct;

    @JsonProperty
    private final IntegralDTO integral;

    @JsonProperty
    private final int newRating;

    public ChallengeAnswerResponse(boolean correct, Integral integral, int newRating) {
        this.correct = correct;
        this.integral = new IntegralDTO(integral);
        this.newRating = newRating;
    }
}
