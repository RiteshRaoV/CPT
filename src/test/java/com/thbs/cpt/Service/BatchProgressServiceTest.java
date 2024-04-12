package com.thbs.cpt.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.Exception.BatchIdNotFoundException;
import com.thbs.cpt.Repository.BatchProgressRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchProgressServiceTest {

    @Mock
    private BatchProgressRepository batchProgressRepository;

    @InjectMocks
    private BatchProgressService batchProgressService;

    @Test
    void testFindBatchwiseProgress() {
        // Given
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{1, 50.0});
        mockResults.add(new Object[]{2, 60.0});
        when(batchProgressRepository.findBatchwiseProgress()).thenReturn(mockResults);

        // When
        List<BatchWiseProgressDTO> result = batchProgressService.findBatchwiseProgress();

        // Then
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getBatchId());
        assertEquals(50.0, result.get(0).getBatchProgress());
        assertEquals(2, result.get(1).getBatchId());
        assertEquals(60.0, result.get(1).getBatchProgress());
    }

    // @Test
    // void testCalculateOverallBatchProgress_Success() throws BatchIdNotFoundException {
    //     // Given
    //     long batchId = 1L;
    //     List<Object[]> mockResults = new ArrayList<>();
    //     mockResults.add(new Object[]{1L, 80.0});
    //     mockResults.add(new Object[]{2L, 85.0});
    //     when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(mockResults);

    //     // When
    //     List<UserBatchProgressDTO> result = batchProgressService.calculateOverallBatchProgress(batchId);

    //     // Then
    //     assertEquals(2, result.size());
    //     assertEquals(1L, result.get(0).getUserId());
    //     assertEquals(80.0, result.get(0).getOverallProgress());
    //     assertEquals(2L, result.get(1).getUserId());
    //     assertEquals(85.0, result.get(1).getOverallProgress());
    // }

    // @Test
    // void testCalculateOverallBatchProgress_BatchIdNotFound() {
    //     // Given
    //     long batchId = 1L;
    //     when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(new ArrayList<>());

    //     // Then
    //     assertThrows(BatchIdNotFoundException.class, () -> batchProgressService.calculateOverallBatchProgress(batchId));
    // }

    @Test
    void testCalculateBatchProgress_Success() throws BatchIdNotFoundException {
        // Given
        int batchId = 1;
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{80.0});
        when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(mockResults);

        // When
        BatchProgressDTO result = batchProgressService.calculateBatchProgress(batchId);

        // Then
        assertEquals(batchId, result.getBatchId());
        assertEquals(80.0, result.getBatchProgress());
    }

    @Test
    void testCalculateBatchProgress_BatchIdNotFound() {
        // Given
        int batchId = 1;
        when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(new ArrayList<>());

        // Then
        assertThrows(BatchIdNotFoundException.class, () -> batchProgressService.calculateBatchProgress(batchId));
    }
}
