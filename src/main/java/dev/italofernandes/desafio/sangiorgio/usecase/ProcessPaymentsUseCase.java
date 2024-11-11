package dev.italofernandes.desafio.sangiorgio.usecase;

import dev.italofernandes.desafio.sangiorgio.domain.Billing;
import dev.italofernandes.desafio.sangiorgio.domain.Seller;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentResponseDTO;
import dev.italofernandes.desafio.sangiorgio.exception.PaymentServiceException;
import dev.italofernandes.desafio.sangiorgio.repository.BillingRepository;
import dev.italofernandes.desafio.sangiorgio.repository.SellerRepository;
import dev.italofernandes.desafio.sangiorgio.service.PaymentService;

import java.util.Optional;

public class ProcessPaymentsUseCase {

    private static final String BILLING_NOT_FOUND_MESSAGE = "Billing not found";
    private static final String SELLER_NOT_FOUND_MESSAGE = "Seller not found";

    private final BillingRepository billingRepository;
    private final SellerRepository sellerRepository;
    private final PaymentService paymentService;

    public ProcessPaymentsUseCase(BillingRepository billingRepository,
                                  SellerRepository sellerRepository,
                                  PaymentService paymentService) {

        this.billingRepository = billingRepository;
        this.sellerRepository = sellerRepository;
        this.paymentService = paymentService;
    }

    public PaymentResponseDTO execute(PaymentRequestDTO paymentRequest) {
        Optional<Seller> seller = sellerRepository.findByCode(paymentRequest.getSellerCode());
        if (seller.isEmpty()) {
            throw new PaymentServiceException(SELLER_NOT_FOUND_MESSAGE);
        }

        paymentRequest.getPayments().forEach(payment -> {
            Optional<Billing> billing = billingRepository.findByCode(payment.getBillingCode());
            if (billing.isEmpty()) {
                throw new PaymentServiceException(BILLING_NOT_FOUND_MESSAGE);
            }
        });

        return paymentService.processPayment(paymentRequest);
    }
}
