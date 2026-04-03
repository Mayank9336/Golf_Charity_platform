package com.digitalheroes.golfcharity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "charities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 2000)
    private String description;

    private String imageUrl;
    private String upcomingEvent;
    private boolean featured;
}
