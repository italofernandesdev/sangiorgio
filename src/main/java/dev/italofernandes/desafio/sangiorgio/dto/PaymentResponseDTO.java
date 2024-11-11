package dev.italofernandes.desafio.sangiorgio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class PaymentResponseDTO extends PaymentRequestDTO {
    public PaymentResponseDTO(String sellerCode, List<PaymentDTO> payments) {
        super(sellerCode, payments);
    }

    public PaymentResponseDTO(PaymentRequestDTO paymentRequestDTO) {
        super(paymentRequestDTO.getSellerCode(), paymentRequestDTO.getPayments());
    }
}
