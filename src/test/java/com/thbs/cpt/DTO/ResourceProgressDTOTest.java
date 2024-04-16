package com.thbs.cpt.DTO;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

public class ResourceProgressDTOTest {


    @Test
    void testAddProgressMultipleResources() {
        // Given
        ResourceProgressDTO progressDTO = new ResourceProgressDTO();
        Long resourceId1 = 1L;
        Double progress1 = 0.75;
        Long resourceId2 = 2L;
        Double progress2 = 0.50;

        // When
        progressDTO.addProgress(resourceId1, progress1);
        progressDTO.addProgress(resourceId2, progress2);

        // Then
        assertEquals(2, progressDTO.getResourceMap().size());
        assertTrue(progressDTO.getResourceMap().containsKey(resourceId1));
        assertTrue(progressDTO.getResourceMap().containsKey(resourceId2));
        assertEquals(progress1, progressDTO.getResourceMap().get(resourceId1));
        assertEquals(progress2, progressDTO.getResourceMap().get(resourceId2));
    }

    @Test
    void testAddProgressSameResourceTwice() {
        // Given
        ResourceProgressDTO progressDTO = new ResourceProgressDTO();
        Long resourceId = 1L;
        Double progress1 = 0.75;
        Double progress2 = 0.50;

        // When
        progressDTO.addProgress(resourceId, progress1);
        progressDTO.addProgress(resourceId, progress2);

        // Then
        assertEquals(1, progressDTO.getResourceMap().size());
        assertTrue(progressDTO.getResourceMap().containsKey(resourceId));
        assertEquals(progress2, progressDTO.getResourceMap().get(resourceId));
    }
    private ResourceProgressDTO resourceProgressDTO;

    @BeforeEach
    public void setUp() {
        // Initialize a new ResourceProgressDTO object before each test
        resourceProgressDTO = new ResourceProgressDTO();
    }

    @Test
    public void testNoArgsConstructorAndGetters() {
        // Test no-args constructor and getters
        assertEquals(null, resourceProgressDTO.getTopicId());
        assertEquals(new HashMap<>(), resourceProgressDTO.getResourceMap());
    }

    @Test
    public void testAllArgsConstructorAndGetters() {
        // Test all-args constructor and getters
        Long topicId = 1L;
        Map<Long, Double> resourceMap = new HashMap<>();
        resourceMap.put(2L, 0.75);
        ResourceProgressDTO dto = new ResourceProgressDTO(topicId, resourceMap);

        assertEquals(topicId, dto.getTopicId());
        assertEquals(resourceMap, dto.getResourceMap());
    }

    @Test
    public void testSetters() {
        // Test setters
        Long topicId = 1L;
        resourceProgressDTO.setTopicId(topicId);
        assertEquals(topicId, resourceProgressDTO.getTopicId());
    }

    @Test
    public void testAddProgress() {
        // Test addProgress method
        Long resourceId = 1L;
        Double progress = 0.5;
        resourceProgressDTO.addProgress(resourceId, progress);

        Map<Long, Double> expectedResourceMap = new HashMap<>();
        expectedResourceMap.put(resourceId, progress);

        assertEquals(expectedResourceMap, resourceProgressDTO.getResourceMap());
    }

    @Test
    public void testAddMultipleProgress() {
        // Test addProgress method for adding multiple progresses
        Long resourceId1 = 1L;
        Double progress1 = 0.5;
        Long resourceId2 = 2L;
        Double progress2 = 0.75;

        resourceProgressDTO.addProgress(resourceId1, progress1);
        resourceProgressDTO.addProgress(resourceId2, progress2);

        Map<Long, Double> expectedResourceMap = new HashMap<>();
        expectedResourceMap.put(resourceId1, progress1);
        expectedResourceMap.put(resourceId2, progress2);

        assertEquals(expectedResourceMap, resourceProgressDTO.getResourceMap());
    }

    @Test
    public void testAddProgressOverwriteExisting() {
        // Test addProgress method for overwriting existing progress
        Long resourceId = 1L;
        Double progress1 = 0.5;
        Double progress2 = 0.75;

        resourceProgressDTO.addProgress(resourceId, progress1);
        resourceProgressDTO.addProgress(resourceId, progress2);

        Map<Long, Double> expectedResourceMap = new HashMap<>();
        expectedResourceMap.put(resourceId, progress2);

        assertEquals(expectedResourceMap, resourceProgressDTO.getResourceMap());
    }
}

