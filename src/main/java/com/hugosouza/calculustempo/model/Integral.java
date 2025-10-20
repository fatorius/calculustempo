package com.hugosouza.calculustempo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "integrals")
public class Integral {
    @Id
    private Long id;

    @Column()
    private String expression_latex;

    @Column()
    private String solution_latex;

    @Column()
    private int rating;

    @Column()
    private double rating_deviation;

    @Column()
    private double volatility;
}
