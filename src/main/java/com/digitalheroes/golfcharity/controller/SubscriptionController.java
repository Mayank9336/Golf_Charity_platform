package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.dto.SubscriptionRequest;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.service.SubscriptionService;
import com.digitalheroes.golfcharity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@CrossOrigin
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @PostMapping("/activate")
    public String activate(Authentication authentication, @RequestBody SubscriptionRequest request) {
        User user = userService.getByEmail(authentication.getName());
        return subscriptionService.activate(user, request.getPlan());
    }

    @PostMapping("/cancel")
    public String cancel(Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
        return subscriptionService.cancel(user);
    }
}
