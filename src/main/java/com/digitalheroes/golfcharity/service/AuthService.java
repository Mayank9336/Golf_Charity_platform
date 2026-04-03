package com.digitalheroes.golfcharity.service;

import com.digitalheroes.golfcharity.config.JwtService;
import com.digitalheroes.golfcharity.dto.AuthRequest;
import com.digitalheroes.golfcharity.dto.AuthResponse;
import com.digitalheroes.golfcharity.dto.SignupRequest;
import com.digitalheroes.golfcharity.model.Role;
import com.digitalheroes.golfcharity.model.SubscriptionStatus;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .subscriptionStatus(SubscriptionStatus.INACTIVE)
                .charityPercentage(10.0)
                .totalWon(0.0)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getRole().name(), user.getEmail());
    }
}
