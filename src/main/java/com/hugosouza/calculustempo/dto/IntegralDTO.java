package com.hugosouza.calculustempo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hugosouza.calculustempo.model.Integral;

public class IntegralDTO {
    @JsonProperty
    private final Long id;

    @JsonProperty
    private final String expression_latex;

    @JsonProperty
    private final String solution_latex;

    @JsonProperty
    private final int rating;

    public IntegralDTO(Integral integral) {
        this.id = integral.getId();
        this.expression_latex = integral.getExpression_latex();
        this.solution_latex = integral.getSolution_latex();
        this.rating = integral.getRating();
    }
}
