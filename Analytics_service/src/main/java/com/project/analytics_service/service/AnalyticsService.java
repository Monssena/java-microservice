package com.project.analytics_service.service;

import com.project.analytics_service.model.PaymentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnalyticsService {

    private final AtomicInteger ordersPaid = new AtomicInteger(0);
    private final AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);

    @KafkaListener(topics = "payment_confirmed", groupId = "analytics-group")
    public void listen(String message) {
        try {
            var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            PaymentEvent event = mapper.readValue(message, PaymentEvent.class);

            if ("paid".equalsIgnoreCase(event.getStatus())) {
                ordersPaid.incrementAndGet();
                totalAmount.updateAndGet(v -> v + event.getAmount());
                System.out.println("✅ Обновлена статистика: " + event);
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при обработке события: " + e.getMessage());
        }
    }

    public int getOrdersPaid() {
        return ordersPaid.get();
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }
}
