package com.project.analytics_service.model;

import lombok.Data;

@Data
public class PaymentEvent {
    private Long order_id;
    private Double amount;
    private String status;
}
