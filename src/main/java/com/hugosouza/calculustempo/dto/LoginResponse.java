package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty
    private final String token;

    @JsonProperty
    private final String refresh_token;

    public LoginResponse(String token, String refresh_token) {
        this.token = token;
        this.refresh_token = refresh_token;
    }
}
