package com.hugosouza.calculustempo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "challenges")
public class Challenge {
    @Id
    private Long id;

    @Column()
    private String expression_latex;

    @Column()
    private String solution_latex;

    @Column()
    private int rating;
}
