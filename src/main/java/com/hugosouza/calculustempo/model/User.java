package com.hugosouza.calculustempo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    @Column(unique = true)
    private String username;

    @Column()
    private String password;

    @Column()
    private int rating;

    @Column()
    private double rating_deviation;

    @Column()
    private double volatility;

    @Column()
    private LocalDateTime last_updated;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rating = 1500;
        this.rating_deviation = 350f;
        this.volatility = 0.06f;
        this.last_updated = LocalDateTime.now();
    }
}