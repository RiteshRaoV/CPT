package com.thbs.cpt.DTO;

import com.thbs.cpt.DTO.ResourceProgressDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceProgressDTOTest {

    @Test
    void testAddProgress() {
        // Given
        ResourceProgressDTO progressDTO = new ResourceProgressDTO();
        Long resourceId = 1L;
        Double progress = 0.75;

        // When
        progressDTO.addProgress(resourceId, progress);

        // Then
        assertTrue(progressDTO.getResourceMap().containsKey(resourceId));
        assertEquals(progress, progressDTO.getResourceMap().get(resourceId));
    }

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
}

