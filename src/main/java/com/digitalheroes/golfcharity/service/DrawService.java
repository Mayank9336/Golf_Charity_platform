package com.digitalheroes.golfcharity.service;

import com.digitalheroes.golfcharity.dto.DrawResultDto;
import com.digitalheroes.golfcharity.model.*;
import com.digitalheroes.golfcharity.repository.DrawRepository;
import com.digitalheroes.golfcharity.repository.ScoreRepository;
import com.digitalheroes.golfcharity.repository.UserRepository;
import com.digitalheroes.golfcharity.repository.WinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final DrawRepository drawRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;
    private final WinnerRepository winnerRepository;

    public DrawResultDto runDraw(DrawMode mode) {
        List<User> activeUsers = userRepository.findAll().stream()
                .filter(u -> u.getSubscriptionStatus() == SubscriptionStatus.ACTIVE)
                .toList();

        List<Integer> winningNumbers = mode == DrawMode.RANDOM
                ? randomNumbers()
                : algorithmicNumbers(activeUsers);

        if (winningNumbers.size() < 5) {
            winningNumbers = randomNumbers();
        }

        String winningNumbersString = winningNumbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        double subscriptionAmount = 1000.0;
        double prizePool = activeUsers.size() * subscriptionAmount * 0.50;
        double previousRollover = drawRepository.findTopByOrderByCreatedAtDesc()
                .map(Draw::getJackpotRollover)
                .orElse(0.0);

        Draw draw = Draw.builder()
                .monthValue(LocalDateTime.now().getMonthValue())
                .yearValue(LocalDateTime.now().getYear())
                .mode(mode)
                .winningNumbers(winningNumbersString)
                .published(true)
                .prizePool(prizePool)
                .jackpotRollover(previousRollover)
                .createdAt(LocalDateTime.now())
                .build();
        drawRepository.save(draw);

        Map<WinnerMatchType, List<User>> winnersMap = new EnumMap<>(WinnerMatchType.class);
        winnersMap.put(WinnerMatchType.MATCH_5, new ArrayList<>());
        winnersMap.put(WinnerMatchType.MATCH_4, new ArrayList<>());
        winnersMap.put(WinnerMatchType.MATCH_3, new ArrayList<>());

        for (User user : activeUsers) {
            List<Integer> userScores = scoreRepository.findTop5ByUserOrderByPlayedDateDesc(user)
                    .stream()
                    .map(Score::getScore)
                    .toList();

            int matches = (int) userScores.stream().filter(winningNumbers::contains).count();
            if (matches >= 5) {
                winnersMap.get(WinnerMatchType.MATCH_5).add(user);
            } else if (matches == 4) {
                winnersMap.get(WinnerMatchType.MATCH_4).add(user);
            } else if (matches == 3) {
                winnersMap.get(WinnerMatchType.MATCH_3).add(user);
            }
        }

        List<String> messages = new ArrayList<>();
        double jackpotShare = prizePool * 0.40 + previousRollover;
        double fourShare = prizePool * 0.35;
        double threeShare = prizePool * 0.25;

        if (winnersMap.get(WinnerMatchType.MATCH_5).isEmpty()) {
            draw.setJackpotRollover(jackpotShare);
            drawRepository.save(draw);
            messages.add("No 5-match winner. Jackpot rolled over: " + jackpotShare);
        } else {
            distribute(draw, winnersMap.get(WinnerMatchType.MATCH_5), WinnerMatchType.MATCH_5, jackpotShare);
            draw.setJackpotRollover(0.0);
            drawRepository.save(draw);
        }

        if (!winnersMap.get(WinnerMatchType.MATCH_4).isEmpty()) {
            distribute(draw, winnersMap.get(WinnerMatchType.MATCH_4), WinnerMatchType.MATCH_4, fourShare);
        }

        if (!winnersMap.get(WinnerMatchType.MATCH_3).isEmpty()) {
            distribute(draw, winnersMap.get(WinnerMatchType.MATCH_3), WinnerMatchType.MATCH_3, threeShare);
        }

        messages.add("Draw completed successfully");
        return new DrawResultDto(draw.getId(), winningNumbersString, prizePool, messages);
    }

    private void distribute(Draw draw, List<User> users, WinnerMatchType type, double totalAmount) {
        if (users.isEmpty()) {
            return;
        }

        double perUser = totalAmount / users.size();
        for (User user : users) {
            Winner winner = Winner.builder()
                    .user(user)
                    .draw(draw)
                    .matchType(type)
                    .amount(perUser)
                    .paymentStatus(PaymentStatus.PENDING)
                    .verified(false)
                    .build();
            winnerRepository.save(winner);

            user.setTotalWon(user.getTotalWon() + perUser);
            userRepository.save(user);
        }
    }

    private List<Integer> randomNumbers() {
        Random random = new Random();
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < 5) {
            set.add(random.nextInt(45) + 1);
        }
        return new ArrayList<>(set);
    }

    private List<Integer> algorithmicNumbers(List<User> users) {
        Map<Integer, Long> freq = new HashMap<>();

        for (User user : users) {
            scoreRepository.findTop5ByUserOrderByPlayedDateDesc(user)
                    .forEach(score -> freq.put(score.getScore(), freq.getOrDefault(score.getScore(), 0L) + 1));
        }

        return freq.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }
}
