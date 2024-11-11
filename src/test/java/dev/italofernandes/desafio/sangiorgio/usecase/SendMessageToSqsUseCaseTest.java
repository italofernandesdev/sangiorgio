package dev.italofernandes.desafio.sangiorgio.usecase;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;
import dev.italofernandes.desafio.sangiorgio.enumeration.PaymentStatusEnum;
import dev.italofernandes.desafio.sangiorgio.exception.AwsSqsServiceException;
import dev.italofernandes.desafio.sangiorgio.service.AwsSqsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SendMessageToSqsUseCaseTest {

    private static final String AWS_SQS_EXPECTED_ERROR_MESSAGE = "AWS SQS message not sent";
    private static final String BILLING_CODE = "BILL01";
    private static final String PAID_AMOUNT = "1000.00";

    @Mock
    private SqsClient sqsClient;

    @Mock
    private AwsSqsService awsSqsService;

    @InjectMocks
    private SendMessageToSqsUseCase sendMessageToSqsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendMessageToAwsSqsQueue() {
        SdkHttpResponse httpResponse = mock(SdkHttpResponse.class);
        when(httpResponse.isSuccessful()).thenReturn(true);
        when(httpResponse.statusCode()).thenReturn(200);

        SendMessageResponse sendMessageResponse = mock(SendMessageResponse.class);
        when(sendMessageResponse.sdkHttpResponse()).thenReturn(httpResponse);
        when(awsSqsService.sendToAwsSqsQueue(getPaymentDTO())).thenReturn(sendMessageResponse);

        SendMessageResponse sendToAwsSqsResponse = sendMessageToSqsUseCase.execute(getPaymentDTO());

        assertTrue(sendToAwsSqsResponse.sdkHttpResponse().isSuccessful());
        assertEquals(200, sendToAwsSqsResponse.sdkHttpResponse().statusCode());
        verify(awsSqsService, times(1)).sendToAwsSqsQueue(getPaymentDTO());
    }

    @Test
    void shouldThrowExceptionWhenSendingMessageToAwsSqsQueue() {
        SdkHttpResponse httpResponse = mock(SdkHttpResponse.class);
        when(httpResponse.isSuccessful()).thenReturn(false);
        when(httpResponse.statusCode()).thenReturn(500);

        SendMessageResponse sendMessageResponse = mock(SendMessageResponse.class);
        when(sendMessageResponse.sdkHttpResponse()).thenReturn(httpResponse);
        when(awsSqsService.sendToAwsSqsQueue(getPaymentDTO())).thenReturn(sendMessageResponse);

        // Act
        AwsSqsServiceException exception = assertThrows(AwsSqsServiceException.class, () -> {
            sendMessageToSqsUseCase.execute(getPaymentDTO());
        });

        assertEquals(AWS_SQS_EXPECTED_ERROR_MESSAGE, exception.getMessage());
    }

    private PaymentDTO getPaymentDTO() {
        return new PaymentDTO(BILLING_CODE, new BigDecimal(PAID_AMOUNT), PaymentStatusEnum.FULL);
    }


}
