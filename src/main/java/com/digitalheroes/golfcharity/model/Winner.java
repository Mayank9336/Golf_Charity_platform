package com.digitalheroes.golfcharity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "winners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "draw_id")
    private Draw draw;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WinnerMatchType matchType;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private String proofUrl;
    private Boolean verified;
}
