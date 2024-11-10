package dev.italofernandes.desafio.sangiorgio.service;

import dev.italofernandes.desafio.sangiorgio.dto.PaymentRequestDTO;
import dev.italofernandes.desafio.sangiorgio.repository.BillingRepository;
import dev.italofernandes.desafio.sangiorgio.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("paymentService")
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final SellerRepository sellerRepository;
    private final BillingRepository billingRepository;

    @Value("${sqs.queue.pago.total}")
    private String queueTotal;

    @Value("${sqs.queue.pago.parcial}")
    private String queueParcial;

    @Value("${sqs.queue.pago.excedente}")
    private String queueExcedente;

    @Override
    public PaymentRequestDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        return paymentRequestDTO;
    }
}
