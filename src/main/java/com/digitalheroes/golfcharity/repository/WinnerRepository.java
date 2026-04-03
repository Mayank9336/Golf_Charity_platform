package com.digitalheroes.golfcharity.repository;

import com.digitalheroes.golfcharity.model.Draw;
import com.digitalheroes.golfcharity.model.User;
import com.digitalheroes.golfcharity.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinnerRepository extends JpaRepository<Winner, Long> {
    List<Winner> findByUser(User user);
    List<Winner> findByDraw(Draw draw);
}
