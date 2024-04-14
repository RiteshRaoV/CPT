package com.thbs.cpt.Controller;
import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.Service.BatchProgressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchProgressControllerTest {

    @Mock
    private BatchProgressService batchProgressService;

    @InjectMocks
    private BatchProgressController batchProgressController;

    @Test
    void testCalculateBatchProgressFound() {
        int batchId = 123;
        BatchProgressDTO mockProgress = new BatchProgressDTO(); // create a mock DTO as per your requirement
        when(batchProgressService.calculateBatchProgress(batchId)).thenReturn(mockProgress);

        ResponseEntity<BatchProgressDTO> response = batchProgressController.calculateBatchProgress(batchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());

        verify(batchProgressService, times(1)).calculateBatchProgress(batchId);
    }

    @Test
    void testCalculateBatchProgressNotFound() {
        int batchId = 456;
        when(batchProgressService.calculateBatchProgress(batchId)).thenReturn(null);

        ResponseEntity<BatchProgressDTO> response = batchProgressController.calculateBatchProgress(batchId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(batchProgressService, times(1)).calculateBatchProgress(batchId);
    }

    @Test
    void testCalculateBatchwiseProgressFound() {
        List<BatchWiseProgressDTO> mockProgressList = Collections.singletonList(new BatchWiseProgressDTO());
        when(batchProgressService.findBatchwiseProgress()).thenReturn(mockProgressList);

        ResponseEntity<List<BatchWiseProgressDTO>> response = batchProgressController.calculateBatchwiseProgress();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgressList, response.getBody());

        verify(batchProgressService, times(1)).findBatchwiseProgress();
    }

    @Test
    void testCalculateBatchwiseProgressNotFound() {
        when(batchProgressService.findBatchwiseProgress()).thenReturn(Collections.emptyList());

        ResponseEntity<List<BatchWiseProgressDTO>> response = batchProgressController.calculateBatchwiseProgress();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(batchProgressService, times(1)).findBatchwiseProgress();
    }

    @Test
    void testGetOverallBatchProgressFound() {
        Long batchId = 789L;
        List<UserBatchProgressDTO> mockProgressList = Collections.singletonList(new UserBatchProgressDTO());
        when(batchProgressService.calculateOverallBatchProgressAllUsers(batchId)).thenReturn(mockProgressList);

        ResponseEntity<List<UserBatchProgressDTO>> response = batchProgressController.getOverallBatchProgress(batchId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgressList, response.getBody());

        verify(batchProgressService, times(1)).calculateOverallBatchProgressAllUsers(batchId);
    }

    @Test
    void testGetOverallBatchProgressNotFound() {
        Long batchId = 999L;
        when(batchProgressService.calculateOverallBatchProgressAllUsers(batchId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserBatchProgressDTO>> response = batchProgressController.getOverallBatchProgress(batchId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(batchProgressService, times(1)).calculateOverallBatchProgressAllUsers(batchId);
    }
}
