package dev.italofernandes.desafio.sangiorgio.response;

public record ProccessPaymentResponse(
        String status,
        int statusCode,
        String message
) {
}
