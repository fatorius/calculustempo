package com.hugosouza.calculustempo.repository;

import com.hugosouza.calculustempo.model.Integral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IntegralRepository extends JpaRepository<Integral, Long> {

    @Query(value = """
        SELECT * FROM integrals
        WHERE rating BETWEEN :minRating AND :maxRating
        ORDER BY RANDOM()
        LIMIT 1
        """, nativeQuery = true)
    Integral findRandomIntegralInRange(
            @Param("minRating") int minRating,
            @Param("maxRating") int maxRating
    );

    @Query(value = """
    SELECT * FROM integrals
    ORDER BY ABS(rating - :targetRating)
    LIMIT 1
    """, nativeQuery = true)
    Integral findClosestIntegralFallback(
            @Param("targetRating") int targetRating
    );
}