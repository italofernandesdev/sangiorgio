package dev.italofernandes.desafio.sangiorgio.exception;

public class AwsSqsException extends RuntimeException{
    public AwsSqsException(String message) {
        super(message);
    }

    public AwsSqsException(String message, Throwable cause) {
        super(message, cause);
    }
}
