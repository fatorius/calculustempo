package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RankingResponse {
    @JsonProperty
    String username;

    @JsonProperty
    int rating;

    public RankingResponse(String username, int rating){
        this.username = username;
        this.rating = rating;
    }
}
