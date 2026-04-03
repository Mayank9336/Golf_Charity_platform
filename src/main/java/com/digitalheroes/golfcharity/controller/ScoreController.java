package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.dto.ScoreRequest;
import com.digitalheroes.golfcharity.model.Score;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.service.ScoreService;
import com.digitalheroes.golfcharity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
@CrossOrigin
public class ScoreController {
    private final ScoreService scoreService;
    private final UserService userService;

    @PostMapping
    public String addScore(Authentication authentication, @Valid @RequestBody ScoreRequest request) {
        User user = userService.getByEmail(authentication.getName());
        return scoreService.addScore(user, request);
    }

    @GetMapping
    public List<Score> getLatestScores(Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
        return scoreService.getLatest5(user);
    }
}
