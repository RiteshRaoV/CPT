package com.thbs.cpt.Exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Given
        String message = "Test message";

        // When
        Exception exception = new Exception(message);

        // Then
        assertEquals(message, exception.getMessage());
        assertNull(exception.getThrowable());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        // Given
        String message = "Test message";
        Throwable cause = new IllegalArgumentException();

        // When
        Exception exception = new Exception(message, cause);

        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getThrowable());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void testConstructorWithMessageAndHttpStatus() {
        // Given
        String message = "Test message";
        HttpStatus status = HttpStatus.NOT_FOUND;

        // When
        Exception exception = new Exception(message, status);

        // Then
        assertEquals(message, exception.getMessage());
        assertNull(exception.getThrowable());
        assertEquals(status, exception.getHttpStatus());
    }

    @Test
    void testConstructorWithMessageCauseAndHttpStatus() {
        // Given
        String message = "Test message";
        Throwable cause = new IllegalArgumentException();
        HttpStatus status = HttpStatus.NOT_FOUND;

        // When
        Exception exception = new Exception(message, cause, status);

        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getThrowable());
        assertEquals(status, exception.getHttpStatus());
    }
}

