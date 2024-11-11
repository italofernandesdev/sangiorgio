package dev.italofernandes.desafio.sangiorgio.usecase;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;
import dev.italofernandes.desafio.sangiorgio.exception.AwsSqsServiceException;
import dev.italofernandes.desafio.sangiorgio.service.AwsSqsService;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.Optional;

public class SendMessageToSqsUseCase {
    private final AwsSqsService awsSqsService;

    public SendMessageToSqsUseCase(AwsSqsService awsSqsService) {
        this.awsSqsService = awsSqsService;
    }


    public SendMessageResponse execute(PaymentDTO payment) {
        SendMessageResponse sendMessageResponse = awsSqsService.sendToAwsSqsQueue(payment);
        if (!sendMessageResponse.sdkHttpResponse().isSuccessful()) {
            throw new AwsSqsServiceException("AWS SQS message not sent");
        }

        return sendMessageResponse;
    }
}
