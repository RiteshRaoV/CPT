package com.thbs.cpt.Exception;

public class TopicIdNotFoundException extends NotFoundException {

    public TopicIdNotFoundException(String message) {
        super(message);
    }

    public TopicIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
