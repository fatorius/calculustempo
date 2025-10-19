package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAChallengeResponse {
    @JsonProperty
    private final Long challengeId;

    @JsonProperty
    private final String challenge;

    public GetAChallengeResponse(Long challengeId, String challenge) {
        this.challengeId = challengeId;
        this.challenge = challenge;
    }
}
