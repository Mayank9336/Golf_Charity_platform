package com.digitalheroes.golfcharity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DrawResultDto {
    private Long drawId;
    private String winningNumbers;
    private Double prizePool;
    private List<String> messages;
}
