package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;

public interface AwsSqsService {
    void sendToAwsSqsQueue(PaymentDTO payment);
}
