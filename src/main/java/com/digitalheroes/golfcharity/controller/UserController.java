package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.repository.WinnerRepository;
import com.digitalheroes.golfcharity.service.ScoreService;
import com.digitalheroes.golfcharity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final ScoreService scoreService;
    private final WinnerRepository winnerRepository;

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard(Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("subscriptionStatus", user.getSubscriptionStatus());
        response.put("subscriptionPlan", user.getSubscriptionPlan());
        response.put("renewalDate", user.getSubscriptionEndDate());
        response.put("selectedCharity", user.getSelectedCharity());
        response.put("charityPercentage", user.getCharityPercentage());
        response.put("scores", scoreService.getLatest5(user));
        response.put("winnings", winnerRepository.findByUser(user));
        response.put("totalWon", user.getTotalWon());
        return response;
    }
}
