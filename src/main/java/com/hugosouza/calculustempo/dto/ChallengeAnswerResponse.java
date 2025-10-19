package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugosouza.calculustempo.model.Integral;

public class ChallengeAnswerResponse {
    @JsonProperty
    private final boolean correct;

    @JsonProperty
    private final IntegralDTO integral;

    public ChallengeAnswerResponse(boolean correct, Integral integral) {
        this.correct = correct;
        this.integral = new IntegralDTO(integral);;
    }
}
