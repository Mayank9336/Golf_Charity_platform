package com.digitalheroes.golfcharity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CharitySelectionRequest {
    @NotNull
    private Long charityId;

    @NotNull
    @Min(10)
    @Max(100)
    private Double charityPercentage;
}
