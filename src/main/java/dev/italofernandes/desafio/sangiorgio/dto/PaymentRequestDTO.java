package dev.italofernandes.desafio.sangiorgio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaymentRequestDTO {
    private String sellerCode;
    private List<PaymentDTO> payments;
}
