package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;

public interface AwsSqsService {
    void sendToAwsSqsQueue(PaymentRequestDTO paymentRequestDTO);
}
