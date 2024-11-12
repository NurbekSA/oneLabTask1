package org.example.persistence.model.exception;

public class UnpaidException extends RuntimeException {
    public UnpaidException(String message) {
        super(message);
    }
}
