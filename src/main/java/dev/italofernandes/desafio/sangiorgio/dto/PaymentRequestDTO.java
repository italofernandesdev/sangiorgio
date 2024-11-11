package dev.italofernandes.desafio.sangiorgio.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentRequestDTO {
    private String sellerCode;
    private List<PaymentDTO> payments;
}
