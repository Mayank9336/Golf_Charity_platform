package com.digitalheroes.golfcharity.controller;

import com.digitalheroes.golfcharity.dto.WinnerVerificationRequest;
import com.digitalheroes.golfcharity.model.Charity;
import com.digitalheroes.golfcharity.model.PaymentStatus;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.model.Winner;
import com.digitalheroes.golfcharity.repository.CharityRepository;
import com.digitalheroes.golfcharity.repository.UserRepository;
import com.digitalheroes.golfcharity.repository.WinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {
    private final UserRepository userRepository;
    private final CharityRepository charityRepository;
    private final WinnerRepository winnerRepository;

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/charities")
    public Charity createCharity(@RequestBody Charity charity) {
        return charityRepository.save(charity);
    }

    @GetMapping("/winners")
    public List<Winner> winners() {
        return winnerRepository.findAll();
    }

    @PutMapping("/winners/{id}/verify")
    public Winner verifyWinner(@PathVariable Long id, @RequestBody WinnerVerificationRequest request) {
        Winner winner = winnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Winner not found"));

        winner.setVerified(request.getVerified());
        winner.setProofUrl(request.getProofUrl());

        if (request.getPaymentStatus() != null && !request.getPaymentStatus().isBlank()) {
            winner.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus().toUpperCase(Locale.ROOT)));
        }

        return winnerRepository.save(winner);
    }
}
