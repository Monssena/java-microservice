package com.project.notification_service.model;

import lombok.Data;

@Data
public class OrderNotification {
    private String email;
    private Integer order_id;
    private String product;
}
