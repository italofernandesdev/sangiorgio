package dev.italofernandes.desafio.sangiorgio.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentRequestDTO {
    private long sellerId;
    private List<PaymentDTO> paymentDTOS;
}
