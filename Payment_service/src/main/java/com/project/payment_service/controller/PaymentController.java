package com.project.payment_service.controller;

import com.project.payment_service.model.PaymentRequest;
import com.project.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> pay(@RequestBody PaymentRequest request) {
        paymentService.processPayment(request);
        return ResponseEntity.accepted().body("Payment processing started");
    }
}
