package com.hugosouza.calculustempo.repository;

import com.hugosouza.calculustempo.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query(value = """
        SELECT * FROM challenges
        WHERE rating BETWEEN :minRating AND :maxRating
        ORDER BY RANDOM()
        LIMIT 1
        """, nativeQuery = true)
    Challenge findRandomChallengeInRange(
            @Param("minRating") int minRating,
            @Param("maxRating") int maxRating
    );
}