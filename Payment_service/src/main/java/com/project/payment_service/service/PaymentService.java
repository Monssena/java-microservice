package com.project.payment_service.service;

import com.project.payment_service.model.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    public void processPayment(PaymentRequest request) {
        try {
            Thread.sleep(3000); // симуляция оплаты
            Map<String, Object> event = new HashMap<>();
            event.put("order_id", request.getOrderId());
            event.put("amount", request.getAmount());
            event.put("status", "paid");

            String message = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(event);
            kafkaTemplate.send("payment_confirmed", message);
            System.out.println("Payment confirmed and sent to Kafka: " + message);
        } catch (Exception e) {
            System.err.println("Ошибка оплаты: " + e.getMessage());
        }
    }
}
