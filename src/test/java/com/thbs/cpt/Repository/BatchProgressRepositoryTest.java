package com.thbs.cpt.Repository;

import com.thbs.cpt.Service.BatchProgressService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BatchProgressRepositoryTest {
    @Mock
    private BatchProgressRepository progressRepository;

    @InjectMocks
    private BatchProgressService batchProgressService;

    @Test
    void testFindOvreallProgressOfUsersInABatch() {
        // Given
        int batchId = 1;
        double expectedBatchProgress = 85.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { expectedBatchProgress });
        when(progressRepository.findOvreallProgressOfUsersInABatch(batchId)).thenReturn(sampleData);

        // When
        List<Object[]> actualProgressList = progressRepository.findOvreallProgressOfUsersInABatch(batchId);

        // Then
        assertEquals(1, actualProgressList.size());
        Object[] result = actualProgressList.get(0);
        assertEquals(expectedBatchProgress, result[0]);
    }

    @Test
    void testFindOverallBatchProgress() {
        // Given
        int batchId = 1;
        double expectedBatchCompletionProgress = 90.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { expectedBatchCompletionProgress });
        when(progressRepository.findOverallBatchProgress(batchId)).thenReturn(sampleData);

        // When
        List<Object[]> actualProgressList = progressRepository.findOverallBatchProgress(batchId);

        // Then
        assertEquals(1, actualProgressList.size());
        Object[] result = actualProgressList.get(0);
        assertEquals(expectedBatchCompletionProgress, result[0]);
    }

    @Test
    void testFindBatchwiseProgress() {
        // Given
        List<Object[]> expectedProgressList = new ArrayList<>();
        expectedProgressList.add(new Object[] { 1, 90.0 });
        expectedProgressList.add(new Object[] { 2, 85.0 });
        when(progressRepository.findBatchwiseProgress()).thenReturn(expectedProgressList);

        // When
        List<Object[]> actualProgressList = progressRepository.findBatchwiseProgress();

        // Then
        assertEquals(expectedProgressList.size(), actualProgressList.size());
        for (int i = 0; i < expectedProgressList.size(); i++) {
            Object[] expectedProgress = expectedProgressList.get(i);
            Object[] actualProgress = actualProgressList.get(i);
            assertEquals(expectedProgress[0], actualProgress[0]);
            assertEquals(expectedProgress[1], actualProgress[1]);
        }
    }


}
