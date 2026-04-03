package com.digitalheroes.golfcharity.service;

import com.digitalheroes.golfcharity.model.SubscriptionPlan;
import com.digitalheroes.golfcharity.model.SubscriptionStatus;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;

    public String activate(User user, SubscriptionPlan plan) {
        if (plan == null) {
            throw new RuntimeException("Subscription plan is required");
        }

        user.setSubscriptionPlan(plan);
        user.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        user.setSubscriptionStartDate(LocalDate.now());
        user.setSubscriptionEndDate(plan == SubscriptionPlan.MONTHLY
                ? LocalDate.now().plusMonths(1)
                : LocalDate.now().plusYears(1));
        userRepository.save(user);
        return "Subscription activated: " + plan;
    }

    public String cancel(User user) {
        user.setSubscriptionStatus(SubscriptionStatus.CANCELLED);
        userRepository.save(user);
        return "Subscription cancelled";
    }
}
