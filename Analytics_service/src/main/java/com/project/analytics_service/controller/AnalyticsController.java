package com.project.analytics_service.controller;

import com.project.analytics_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public Map<String, Object> getStats() {
        return Map.of(
                "orders_paid", analyticsService.getOrdersPaid(),
                "total_amount", analyticsService.getTotalAmount()
        );
    }
}
