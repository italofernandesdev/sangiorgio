package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.domain.Billing;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentDTO;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;
import dev.italofernandes.desafio.sangiorgio.dto.PaymentResponseDTO;
import dev.italofernandes.desafio.sangiorgio.enumeration.PaymentStatusEnum;
import dev.italofernandes.desafio.sangiorgio.exception.PaymentServiceException;
import dev.italofernandes.desafio.sangiorgio.repository.BillingRepository;
import dev.italofernandes.desafio.sangiorgio.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service("paymentService")
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private SellerRepository sellerRepository;
    private BillingRepository billingRepository;
    private AwsSqsService awsSqsService;

    @Autowired
    public PaymentServiceImpl(SellerRepository sellerRepository,
                              BillingRepository billingRepository,
                              AwsSqsService awsSqsService) {

        this.sellerRepository = sellerRepository;
        this.billingRepository = billingRepository;
        this.awsSqsService = awsSqsService;
    }

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) {
        validateSeller(paymentRequest.getSellerCode());
        validateBilling(paymentRequest);

        paymentRequest.getPayments().forEach(payment -> {
            payment.setStatus(getPaymentStatus(payment));

            awsSqsService.sendToAwsSqsQueue(payment);
        });

        return new PaymentResponseDTO(paymentRequest);
    }

    private void validateSeller(String sellerCode) {
        if(sellerRepository.findByCode(sellerCode).isEmpty()) {
            throw new PaymentServiceException("Seller not found with code: " + sellerCode);
        }
    }

    private void validateBilling(PaymentRequestDTO paymentRequest) {
        List<String> billingCodesNotFound = new ArrayList<>();
        paymentRequest.getPayments().forEach(payment -> {
            if(billingRepository.findByCode(payment.getBillingCode()).isEmpty()) {
                billingCodesNotFound.add(payment.getBillingCode());
            }
        });

        if(!billingCodesNotFound.isEmpty()) {
            throw new PaymentServiceException("Billings not found with codes " + billingCodesNotFound.toString());
        }
    }

    private PaymentStatusEnum getPaymentStatus(PaymentDTO payment) {
        AtomicReference<PaymentStatusEnum> paymentStatus = new AtomicReference<>();

        Optional<Billing> billingFound = billingRepository.findByCode(payment.getBillingCode());

        billingFound.ifPresent(billing -> {
            int comparisonResult = payment.getPaidAmount().compareTo(billing.getOriginalAmount());

            if (comparisonResult > 0) {
                paymentStatus.set(PaymentStatusEnum.EXCESS);
            } else if (comparisonResult < 0) {
                paymentStatus.set(PaymentStatusEnum.PARTIAL);
            } else {
                paymentStatus.set(PaymentStatusEnum.FULL);
            }
        });

        return paymentStatus.get();
    }
}
