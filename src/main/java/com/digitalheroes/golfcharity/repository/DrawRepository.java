package com.digitalheroes.golfcharity.repository;

import com.digitalheroes.golfcharity.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrawRepository extends JpaRepository<Draw, Long> {
    Optional<Draw> findTopByOrderByCreatedAtDesc();
}
