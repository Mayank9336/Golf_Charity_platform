package com.digitalheroes.golfcharity.service;

import com.digitalheroes.golfcharity.dto.CharitySelectionRequest;
import com.digitalheroes.golfcharity.model.Charity;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.repository.CharityRepository;
import com.digitalheroes.golfcharity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityService {
    private final CharityRepository charityRepository;
    private final UserRepository userRepository;

    public List<Charity> getAll() {
        return charityRepository.findAll();
    }

    public String selectCharity(User user, CharitySelectionRequest request) {
        Charity charity = charityRepository.findById(request.getCharityId())
                .orElseThrow(() -> new RuntimeException("Charity not found"));

        user.setSelectedCharity(charity);
        user.setCharityPercentage(request.getCharityPercentage());
        userRepository.save(user);
        return "Charity selected successfully";
    }

    public Charity create(Charity charity) {
        return charityRepository.save(charity);
    }
}
