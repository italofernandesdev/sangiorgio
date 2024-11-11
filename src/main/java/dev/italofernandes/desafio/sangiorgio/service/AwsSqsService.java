package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public interface AwsSqsService {
    SendMessageResponse sendToAwsSqsQueue(PaymentDTO payment);
}
