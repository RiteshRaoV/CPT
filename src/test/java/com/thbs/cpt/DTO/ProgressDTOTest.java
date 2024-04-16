package com.thbs.cpt.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ProgressDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data
        Long userId = 1L;
        List<CourseDTO> courses = Arrays.asList(new CourseDTO(1L, 50.0, null), new CourseDTO(2L, 75.0, null));

        // Create a ProgressDTO object
        ProgressDTO progressDTO = new ProgressDTO(userId, courses);

        // Check if constructor and getters work correctly
        assertEquals(userId, progressDTO.getUserId());
        assertEquals(courses, progressDTO.getCourses());
    }

    @Test
    void testSetter() {
        // Create a ProgressDTO object
        ProgressDTO progressDTO = new ProgressDTO();

        // Set sample data
        Long userId = 1L;
        List<CourseDTO> courses = Arrays.asList(new CourseDTO(1L, 50.0, null), new CourseDTO(2L, 75.0, null));
        progressDTO.setUserId(userId);
        progressDTO.setCourses(courses);

        // Check if setters work correctly
        assertEquals(userId, progressDTO.getUserId());
        assertEquals(courses, progressDTO.getCourses());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a ProgressDTO object using no-args constructor
        ProgressDTO progressDTO = new ProgressDTO();

        // Check if object is not null
        assertNotNull(progressDTO);
    }
}

