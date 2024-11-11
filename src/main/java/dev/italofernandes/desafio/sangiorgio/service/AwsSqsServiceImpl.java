package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;
import dev.italofernandes.desafio.sangiorgio.enumeration.PaymentStatusEnum;
import dev.italofernandes.desafio.sangiorgio.exception.AwsSqsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Service("awsSqsService")
public class AwsSqsServiceImpl implements AwsSqsService {
    private final SqsClient sqsClient;
    private final String partialPaymentQueue;
    private final String fullPaymentQueue;
    private final String excessPaymentQueue;

    public AwsSqsServiceImpl(SqsClient sqsClient,
                      @Value("${aws.sqs.partial-payment-queue}") String partialPaymentQueue,
                      @Value("${aws.sqs.full-payment-queue}") String fullPaymentQueue,
                      @Value("${aws.sqs.excess-payment-queue}") String excessPaymentQueue) {
        this.sqsClient = sqsClient;
        this.partialPaymentQueue = partialPaymentQueue;
        this.fullPaymentQueue = fullPaymentQueue;
        this.excessPaymentQueue = excessPaymentQueue;
    }

    @Override
    public void sendToAwsSqsQueue(PaymentDTO payment) throws AwsSqsException {
        String sqsQueueUrl = getQueueByPaymentStatus(payment.getStatus());

        try {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(sqsQueueUrl)
                    .messageBody(new Gson().toJson(payment))
                    .build());
        } catch (SqsException e) {
            throw new AwsSqsException("An error occurred trying to send the message to AWS SQS queue: " + sqsQueueUrl,  e.getCause());
        }
    }

    private String getQueueByPaymentStatus(PaymentStatusEnum paymentStatusEnum) {
        return switch (paymentStatusEnum) {
            case PARTIAL -> partialPaymentQueue;
            case EXCESS -> excessPaymentQueue;
            case FULL -> fullPaymentQueue;
            default -> throw new AwsSqsException(
                    "Payment Status not found: " + paymentStatusEnum.name()
            );
        };
    }
}
