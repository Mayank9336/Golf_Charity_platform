package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.dto.CharitySelectionRequest;
import com.digitalheroes.golfcharity.model.Charity;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.service.CharityService;
import com.digitalheroes.golfcharity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charities")
@RequiredArgsConstructor
@CrossOrigin
public class CharityController {
    private final CharityService charityService;
    private final UserService userService;

    @GetMapping
    public List<Charity> getAll() {
        return charityService.getAll();
    }

    @PostMapping("/select")
    public String select(Authentication authentication, @RequestBody CharitySelectionRequest request) {
        User user = userService.getByEmail(authentication.getName());
        return charityService.selectCharity(user, request);
    }
}
