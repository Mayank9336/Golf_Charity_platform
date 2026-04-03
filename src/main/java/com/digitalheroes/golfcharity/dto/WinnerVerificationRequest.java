package com.digitalheroes.golfcharity.dto;

import lombok.Data;

@Data
public class WinnerVerificationRequest {
    private Boolean verified;
    private String proofUrl;
    private String paymentStatus;
}
