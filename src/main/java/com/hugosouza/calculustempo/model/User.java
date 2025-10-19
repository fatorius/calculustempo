package com.hugosouza.calculustempo.model;

import jakarta.persistence.*;
import lombok.*;

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

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rating = 1500;
    }
}