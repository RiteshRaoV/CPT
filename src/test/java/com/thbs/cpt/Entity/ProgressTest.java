package com.thbs.cpt.Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProgressTest {

    @Test
    public void testProgressConstructor() {
        // Arrange
        long userId = 123;
        long resourceId = 456;
        double completionPercentage = 75.5;

        // Act
        Progress progress = new Progress(userId, resourceId, completionPercentage);

        // Assert
        assertEquals(userId, progress.getUserId());
        assertEquals(resourceId, progress.getResourceId());
        assertEquals(completionPercentage, progress.getCompletionPercentage());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        Progress progress = new Progress();
        long userId = 123;
        long resourceId = 456;
        double completionPercentage = 75.5;

        // Act
        progress.setUserId(userId);
        progress.setResourceId(resourceId);
        progress.setCompletionPercentage(completionPercentage);

        // Assert
        assertEquals(userId, progress.getUserId());
        assertEquals(resourceId, progress.getResourceId());
        assertEquals(completionPercentage, progress.getCompletionPercentage());
    }

    @Test
    public void testEquality() {
        // Arrange
        Progress progress1 = new Progress(1L, 2L, 50.0);
        Progress progress2 = new Progress(1L, 2L, 50.0);
        Progress progress3 = new Progress(1L, 3L, 75.0);

        // Assert
        assertEquals(progress1, progress2); // Two progress objects with the same values are equal
        assertNotEquals(progress1, progress3); // Two progress objects with different values are not equal
    }

    @Test
    public void testHashCode() {
        // Arrange
        Progress progress1 = new Progress(1L, 2L, 50.0);
        Progress progress2 = new Progress(1L, 2L, 50.0);

        // Assert
        assertEquals(progress1.hashCode(), progress2.hashCode()); // Equal objects must have the same hash code
    }
    @Test
    public void testSetProgressId() {
        // Create a Progress object
        Progress progress = new Progress();

        // Set the progressId
        long progressId = 123;
        progress.setProgressId(progressId);

        // Verify that the progressId is set correctly
        assertEquals(progressId, progress.getProgressId());
    }

    @Test
    public void testSetBatchId() {
        // Create a Progress object
        Progress progress = new Progress();

        // Set the batchId
        long batchId = 456;
        progress.setBatchId(batchId);

        // Verify that the batchId is set correctly
        assertEquals(batchId, progress.getBatchId());
    }
}
