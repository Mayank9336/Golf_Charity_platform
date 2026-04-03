package com.digitalheroes.golfcharity.config;

import com.digitalheroes.golfcharity.model.*;
import com.digitalheroes.golfcharity.repository.CharityRepository;
import com.digitalheroes.golfcharity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (charityRepository.count() == 0) {
            charityRepository.save(Charity.builder()
                    .name("Helping Hands Foundation")
                    .description("Supports children and education.")
                    .imageUrl("")
                    .upcomingEvent("Golf Day - April")
                    .featured(true)
                    .build());

            charityRepository.save(Charity.builder()
                    .name("Green Earth Trust")
                    .description("Supports environmental recovery.")
                    .imageUrl("")
                    .upcomingEvent("Charity Cup - May")
                    .featured(false)
                    .build());

            charityRepository.save(Charity.builder()
                    .name("Care For All")
                    .description("Supports healthcare access.")
                    .imageUrl("")
                    .upcomingEvent("Community Drive - June")
                    .featured(false)
                    .build());
        }

        if (!userRepository.existsByEmail("admin@golf.com")) {
            userRepository.save(User.builder()
                    .name("Admin")
                    .email("admin@golf.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ROLE_ADMIN)
                    .subscriptionStatus(SubscriptionStatus.ACTIVE)
                    .charityPercentage(10.0)
                    .totalWon(0.0)
                    .build());
        }
    }
}
