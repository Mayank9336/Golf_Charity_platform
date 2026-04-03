package com.digitalheroes.golfcharity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "draws")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Draw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer monthValue;

    @Column(nullable = false)
    private Integer yearValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DrawMode mode;

    @Column(nullable = false)
    private String winningNumbers;

    @Column(nullable = false)
    private boolean published;

    @Column(nullable = false)
    private Double prizePool;

    @Column(nullable = false)
    private Double jackpotRollover;

    private LocalDateTime createdAt;
}
