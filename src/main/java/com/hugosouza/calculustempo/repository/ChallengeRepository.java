package com.hugosouza.calculustempo.repository;

import com.hugosouza.calculustempo.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query(value = """
        SELECT * FROM challenges
        WHERE user_id = :user_id AND result_success IS NULL
        LIMIT 1
        """, nativeQuery = true)
    Optional<Challenge> findUserActiveChallenge(
            @Param("user_id") Long user_id
    );

    @Query(value = """
        SELECT * FROM challenges
        WHERE user_id = :user_id AND result_success IS NULL
        LIMIT 1
        """, nativeQuery = true)
    Challenge getUserActiveChallenge(
            @Param("user_id") Long user_id
    );
}
