package dev.italofernandes.desafio.sangiorgio.usecase;

import dev.italofernandes.desafio.sangiorgio.domain.Billing;
import dev.italofernandes.desafio.sangiorgio.domain.Seller;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentResponseDTO;
import dev.italofernandes.desafio.sangiorgio.enumeration.PaymentStatusEnum;
import dev.italofernandes.desafio.sangiorgio.exception.PaymentServiceException;
import dev.italofernandes.desafio.sangiorgio.repository.BillingRepository;
import dev.italofernandes.desafio.sangiorgio.repository.SellerRepository;
import dev.italofernandes.desafio.sangiorgio.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessPaymentsUseCaseTest {

    private static final String BILLING_CODE = "BILL01";
    private static final String SELLER_CODE = "SELL01";
    private static final String PAID_AMOUNT = "1000.00";
    private static final String BILLING_NOT_FOUND_MESSAGE = "Billing not found";
    private static final String SELLER_NOT_FOUND_MESSAGE = "Seller not found";

    @Mock
    PaymentService paymentService;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private BillingRepository billingRepository;

    @InjectMocks
    private ProcessPaymentsUseCase processPaymentsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessPaymentsWhenSellerAndBillingExists() {
        // Arrange
        when(sellerRepository.findByCode(SELLER_CODE))
                .thenReturn(Optional.of(new Seller(1, SELLER_CODE)));

        when(billingRepository.findByCode(BILLING_CODE))
                .thenReturn(Optional.of(new Billing(1, BILLING_CODE, new BigDecimal(PAID_AMOUNT))));

        PaymentRequestDTO paymentRequest = getPaymentRequest();

        PaymentResponseDTO paymentResponse = new PaymentResponseDTO(
                SELLER_CODE,
                List.of(new PaymentDTO(BILLING_CODE, new BigDecimal(PAID_AMOUNT), PaymentStatusEnum.FULL))
        );

        when(paymentService.processPayment(paymentRequest)).thenReturn(paymentResponse);

        // Act
        PaymentResponseDTO paymentResponseUseCase = processPaymentsUseCase.execute(paymentRequest);

        // Asserts
        assertNotNull(paymentResponseUseCase);
        assertEquals(SELLER_CODE, paymentResponseUseCase.getSellerCode());
        assertEquals(1, paymentResponseUseCase.getPayments().size());
        verify(paymentService, times(1)).processPayment(paymentRequest);

    }

    @Test
    void shouldThrowExceptionWhenSellerNotExists() {
        // Arrange
        PaymentRequestDTO paymentRequest = getPaymentRequest();
        when(sellerRepository.findByCode(SELLER_CODE)).thenReturn(Optional.empty());

        // Act
        PaymentServiceException exception = assertThrows(PaymentServiceException.class, () -> {
            processPaymentsUseCase.execute(paymentRequest);
        });

        assertEquals(SELLER_NOT_FOUND_MESSAGE, exception.getMessage());
        verify(paymentService, never()).processPayment(any());
    }

    @Test
    void shouldThrowExceptionWhenBillingNotExists() {
        // Arrange
        PaymentRequestDTO paymentRequest = getPaymentRequest();

        when(sellerRepository.findByCode(SELLER_CODE))
                .thenReturn(Optional.of(new Seller(1, SELLER_CODE)));

        when(billingRepository.findByCode(BILLING_CODE)).thenReturn(Optional.empty());

        // Act
        PaymentServiceException exception = assertThrows(PaymentServiceException.class, () -> {
            processPaymentsUseCase.execute(paymentRequest);
        });

        assertEquals(BILLING_NOT_FOUND_MESSAGE, exception.getMessage());
        verify(paymentService, never()).processPayment(any());
    }

    private PaymentRequestDTO getPaymentRequest() {
        return new PaymentRequestDTO(SELLER_CODE,
                List.of(new PaymentDTO(BILLING_CODE, new BigDecimal(PAID_AMOUNT), null))
        );
    }


}
