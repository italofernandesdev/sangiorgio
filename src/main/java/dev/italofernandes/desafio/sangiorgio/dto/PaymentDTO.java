package dev.italofernandes.desafio.sangiorgio.dto;

import dev.italofernandes.desafio.sangiorgio.enumeration.PaymentStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    private String billingCode;
    private BigDecimal paidAmount;
    private PaymentStatusEnum status;
}
