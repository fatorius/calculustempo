package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChallengeAnswerRequest {
    @JsonProperty
    private final String answer;

    public ChallengeAnswerRequest(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
