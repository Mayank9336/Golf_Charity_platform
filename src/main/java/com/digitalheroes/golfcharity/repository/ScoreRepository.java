package com.digitalheroes.golfcharity.repository;

import com.digitalheroes.golfcharity.model.Score;
import com.digitalheroes.golfcharity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByUserOrderByPlayedDateDesc(User user);
    List<Score> findTop5ByUserOrderByPlayedDateDesc(User user);
}
