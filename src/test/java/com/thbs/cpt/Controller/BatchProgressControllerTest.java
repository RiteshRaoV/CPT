package com.thbs.cpt.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.Exception.BatchIdNotFoundException;
import com.thbs.cpt.Service.BatchProgressService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchProgressControllerTest {

    @Mock
    private BatchProgressService batchProgressService;

    @InjectMocks
    private BatchProgressController batchProgressController;

    @Test
    void testCalculateBatchProgress_Success() {
        // Given
        int batchId = 1;
        BatchProgressDTO expectedProgress = new BatchProgressDTO(batchId, 75.0);
        when(batchProgressService.calculateBatchProgress(batchId)).thenReturn(expectedProgress);

        // When
        ResponseEntity<BatchProgressDTO> response = batchProgressController.calculateBatchProgress(batchId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProgress, response.getBody());
    }

    @Test
    void testCalculateBatchProgress_NotFound() {
        // Given
        int batchId = 1;
        when(batchProgressService.calculateBatchProgress(batchId)).thenReturn(null);

        // When
        ResponseEntity<BatchProgressDTO> response = batchProgressController.calculateBatchProgress(batchId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCalculateBatchwiseProgress_Success() {
        // Given
        List<BatchWiseProgressDTO> expectedProgressList = new ArrayList<>();
        expectedProgressList.add(new BatchWiseProgressDTO(1, 75.0));
        when(batchProgressService.findBatchwiseProgress()).thenReturn(expectedProgressList);

        // When
        ResponseEntity<List<BatchWiseProgressDTO>> response = batchProgressController.calculateBatchwiseProgress();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProgressList, response.getBody());
    }

    @Test
    void testCalculateBatchwiseProgress_NotFound() {
        // Given
        when(batchProgressService.findBatchwiseProgress()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<BatchWiseProgressDTO>> response = batchProgressController.calculateBatchwiseProgress();

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetOverallBatchProgress_Success() {
        // Given
        long batchId = 1L;
        List<UserBatchProgressDTO> expectedProgressList = new ArrayList<>();
        expectedProgressList.add(new UserBatchProgressDTO(1L, 80.0));
        expectedProgressList.add(new UserBatchProgressDTO(2L, 85.0));
        when(batchProgressService.calculateOverallBatchProgress(batchId)).thenReturn(expectedProgressList);

        // When
        ResponseEntity<List<UserBatchProgressDTO>> response = batchProgressController.getOverallBatchProgress(batchId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProgressList, response.getBody());
    }


    
}
