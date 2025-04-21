package com.project.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.notification_service.model.OrderNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "orders", groupId = "notification-group")
    public void consume(String message) {
        try {
            OrderNotification notif = objectMapper.readValue(message, OrderNotification.class);
            sendEmail(notif);
        } catch (Exception e) {
            System.err.println("❌ Ошибка при обработке сообщения: " + e.getMessage());
        }
    }

    private void sendEmail(OrderNotification notif) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notif.getEmail());
        message.setSubject("📦 Новый заказ");
        message.setText("Поступил заказ #" + notif.getOrder_id() + " на товар: " + notif.getProduct());

        mailSender.send(message);
        System.out.println("📨 Email отправлен на " + notif.getEmail());
    }
}
