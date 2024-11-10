package dev.italofernandes.desafio.sangiorgio.controller;

import dev.italofernandes.desafio.sangiorgio.domain.Payment;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;
import dev.italofernandes.desafio.sangiorgio.response.ProccessPaymentResponse;
import dev.italofernandes.desafio.sangiorgio.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> processPayments(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            PaymentRequestDTO response = paymentService.processPayment(paymentRequestDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
