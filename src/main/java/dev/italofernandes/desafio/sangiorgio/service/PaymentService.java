package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest);
}
