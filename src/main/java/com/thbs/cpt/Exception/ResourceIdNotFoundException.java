package com.thbs.cpt.Exception;

public class ResourceIdNotFoundException extends NotFoundException {

    public ResourceIdNotFoundException(String message) {
        super(message);
    }

    public ResourceIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
