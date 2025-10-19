package com.hugosouza.calculustempo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Integer user_id;

    @Column()
    private Long integral_id;

    @Column()
    private Boolean result_success;

    @Column()
    private Integer new_user_rating;

    @Column()
    private Integer new_integral_rating;

    @Column()
    private Date played_at = new Date();

    public Challenge(Long id, Long integralId) {
        this.id = id;
        this.integral_id = integralId;
    }
}
