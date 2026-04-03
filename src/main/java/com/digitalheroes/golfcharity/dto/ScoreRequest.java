package com.digitalheroes.golfcharity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScoreRequest {
    @NotNull
    @Min(1)
    @Max(45)
    private Integer score;

    @NotNull
    private LocalDate playedDate;
}
