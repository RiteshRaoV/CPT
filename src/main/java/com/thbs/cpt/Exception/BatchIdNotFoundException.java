package com.thbs.cpt.Exception;

public class BatchIdNotFoundException extends NotFoundException {

    public BatchIdNotFoundException(String message) {
        super(message);
    }

    public BatchIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
