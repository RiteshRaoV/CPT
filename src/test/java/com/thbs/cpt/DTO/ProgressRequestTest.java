package com.thbs.cpt.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ProgressRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data
        long userId = 1L;
        long batchId = 1L;

        List<Long> courseIds = Arrays.asList(1L, 2L, 3L);

        // Create a ProgressRequest object
        ProgressRequest progressRequest = new ProgressRequest(userId,batchId, courseIds);

        // Check if constructor and getters work correctly
        assertEquals(userId, progressRequest.getUserId());
        assertEquals(courseIds, progressRequest.getCourseIds());
    }

    @Test
    void testSetter() {
        // Create a ProgressRequest object
        ProgressRequest progressRequest = new ProgressRequest();

        // Set sample data
        long userId = 1L;
        List<Long> courseIds = Arrays.asList(1L, 2L, 3L);
        progressRequest.setUserId(userId);
        progressRequest.setCourseIds(courseIds);

        // Check if setters work correctly
        assertEquals(userId, progressRequest.getUserId());
        assertEquals(courseIds, progressRequest.getCourseIds());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a ProgressRequest object using no-args constructor
        ProgressRequest progressRequest = new ProgressRequest();

        // Check if object is not null
        assertNotNull(progressRequest);
    }
}

