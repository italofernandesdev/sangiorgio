package dev.italofernandes.desafio.sangiorgio.exception;

public class AwsSqsServiceException extends RuntimeException{
    public AwsSqsServiceException(String message) {
        super(message);
    }

    public AwsSqsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
