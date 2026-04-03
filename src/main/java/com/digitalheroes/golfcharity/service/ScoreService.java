package com.digitalheroes.golfcharity.service;

import com.digitalheroes.golfcharity.dto.ScoreRequest;
import com.digitalheroes.golfcharity.model.Score;
import com.digitalheroes.golfcharity.model.SubscriptionStatus;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public String addScore(User user, ScoreRequest request) {
        if (user.getSubscriptionStatus() != SubscriptionStatus.ACTIVE) {
            throw new RuntimeException("Only active subscribers can add scores");
        }

        Score score = Score.builder()
                .user(user)
                .score(request.getScore())
                .playedDate(request.getPlayedDate())
                .build();

        scoreRepository.save(score);

        List<Score> allScores = scoreRepository.findByUserOrderByPlayedDateDesc(user);
        if (allScores.size() > 5) {
            Score oldest = allScores.stream()
                    .min(Comparator.comparing(Score::getPlayedDate).thenComparing(Score::getId))
                    .orElse(null);
            if (oldest != null) {
                scoreRepository.delete(oldest);
            }
        }

        return "Score added successfully";
    }

    public List<Score> getLatest5(User user) {
        return scoreRepository.findTop5ByUserOrderByPlayedDateDesc(user);
    }
}
