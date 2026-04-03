package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.dto.AuthRequest;
import com.digitalheroes.golfcharity.dto.AuthResponse;
import com.digitalheroes.golfcharity.dto.SignupRequest;
import com.digitalheroes.golfcharity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
