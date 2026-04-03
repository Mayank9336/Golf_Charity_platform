package com.digitalheroes.golfcharity.dto;

import com.digitalheroes.golfcharity.model.SubscriptionPlan;
import lombok.Data;

@Data
public class SubscriptionRequest {
    private SubscriptionPlan plan;
}
