package com.thbs.cpt.Service;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.Exception.BatchIdNotFoundException;
import com.thbs.cpt.Repository.BatchProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchProgressServiceTest {

    @Mock
    private BatchProgressRepository batchProgressRepository;

    @Mock
    private UserProgressService userProgressService;

    @InjectMocks
    private BatchProgressService batchProgressService;

    
    @Test
    void testFindBatchwiseProgress() throws BatchIdNotFoundException {
        // Mocking repository response
        List<Object[]> batches = new ArrayList<>();
        batches.add(new Object[]{1L}); // Assuming there is a batch with ID 1
        when(batchProgressRepository.findAllBatches()).thenReturn(batches);
    
        // Stubbing the calculateBatchProgress method
        when(batchProgressService.calculateBatchProgress(eq(1L))).thenReturn(new BatchProgressDTO(1L, 0.75)); // Verify batch ID 1
    
        // Test
        List<BatchWiseProgressDTO> result = batchProgressService.findBatchwiseProgress();
    
        // Assertions
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getBatchId());
        assertEquals(0.75, result.get(0).getBatchProgress());
        verify(batchProgressRepository, times(1)).findAllBatches();
        verify(batchProgressService, times(1)).calculateBatchProgress(eq(1L)); // Verify batch progress calculation for batch ID 1
    }
    
    
    

    @Test
    void testCalculateOverallBatchProgressAllUsers() throws BatchIdNotFoundException {
        long batchId = 1L;

        // Mocking repository response
        List<Object[]> users = new ArrayList<>();
        users.add(new Object[] { 1L });
        when(batchProgressRepository.findAllUsers(batchId)).thenReturn(users);

        // Mocking user progress calculation
        when(userProgressService.calculateOverallProgressForUser(1L)).thenReturn(new UserProgressDTO());

        List<UserBatchProgressDTO> result = batchProgressService.calculateOverallBatchProgressAllUsers(batchId);

        // Assertions
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
        assertNotNull(result.get(0).getOverallProgress());
        verify(batchProgressRepository, times(1)).findAllUsers(batchId);
        verify(userProgressService, times(1)).calculateOverallProgressForUser(1L);
    }

    @Test
    void testCalculateOverallBatchProgressAllUsersBatchNotFound() {
        long batchId = 999L;
        when(batchProgressRepository.findAllUsers(batchId)).thenReturn(Collections.emptyList());

        assertThrows(BatchIdNotFoundException.class,
                () -> batchProgressService.calculateOverallBatchProgressAllUsers(batchId));

        verify(batchProgressRepository, times(1)).findAllUsers(batchId);
        verifyNoInteractions(userProgressService);
    }

    @Test
    void testCalculateBatchProgress() throws BatchIdNotFoundException {
        long batchId = 1L;
        Object[] result = new Object[] { 0.75 }; // Assuming batch progress is 75%
        List<Object[]> results = Collections.singletonList(result);
        when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(results);

        BatchProgressDTO dto = batchProgressService.calculateBatchProgress(batchId);

        assertEquals(batchId, dto.getBatchId());
        assertEquals(0.75, dto.getBatchProgress());
        verify(batchProgressRepository, times(1)).findOverallBatchProgress(batchId);
    }

    @Test
    void testCalculateBatchProgressBatchNotFound() {
        long batchId = 999L;
        when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(Collections.emptyList());

        assertThrows(BatchIdNotFoundException.class, () -> batchProgressService.calculateBatchProgress(batchId));

        verify(batchProgressRepository, times(1)).findOverallBatchProgress(batchId);
    }
}
