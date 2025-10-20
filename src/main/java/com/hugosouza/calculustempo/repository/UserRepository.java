package com.hugosouza.calculustempo.repository;

import com.hugosouza.calculustempo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = """
        SELECT * FROM users
        WHERE rating_deviation < 350
        """, nativeQuery = true)
    Page<User> findAllActiveUsers(Pageable pageable);
}