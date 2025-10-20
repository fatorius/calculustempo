package com.hugosouza.calculustempo.service;

import com.hugosouza.calculustempo.model.Integral;
import com.hugosouza.calculustempo.model.User;
import com.hugosouza.calculustempo.repository.UserRepository;
import com.hugosouza.calculustempo.util.Glicko2;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public int updateUserRatings(User user, Integral integral, boolean correct){
        double sUser = correct ? 1.0 : 0.0;

        double[] userUpdate = Glicko2.update(
                user.getRating(),
                user.getRating_deviation(),
                user.getVolatility(),
                integral.getRating(),
                integral.getRating_deviation(),
                sUser
        );

        user.setRating((int) userUpdate[0]);
        user.setRating_deviation(userUpdate[1]);
        user.setVolatility(userUpdate[2]);

        return (int) userUpdate[0];
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateInactiveUsersRD() {
        int page = 0;

        int PAGE_SIZE = 500;

        Page<User> usersPage;

        do {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE);
            usersPage = userRepository.findAllActiveUsers(pageable);

            for (User user : usersPage.getContent()) {
                LocalDateTime lastActive = user.getLast_updated();
                long daysInactive = ChronoUnit.DAYS.between(lastActive, LocalDateTime.now());
                if (daysInactive <= 0) continue;

                double rd = user.getRating_deviation();
                double sigma = user.getVolatility();

                double weeksInactive = daysInactive / 7.0;
                rd = Math.sqrt(rd * rd + sigma * sigma * weeksInactive);
                if (rd > 350) rd = 350;

                user.setRating_deviation(rd);
            }

            userRepository.flush();
            entityManager.clear();

            page++;

        } while (usersPage.hasNext());
    }
}
