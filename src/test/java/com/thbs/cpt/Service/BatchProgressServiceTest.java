package com.thbs.cpt.Service;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.Exception.BatchIdNotFoundException;
import com.thbs.cpt.Repository.BatchProgressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
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

    
@BeforeEach
void setUp() {
    // Initialize Mockito annotations
    MockitoAnnotations.initMocks(this);
}


    // Add similar test cases for other methods as needed


    
    

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



    ///// find batchwise progress
    @Test
    void testFindBatchwiseProgressWhenNoBatchesFound() {
        // Given
        when(batchProgressRepository.findAllBatches()).thenReturn(new ArrayList<>());

        // When
        List<BatchWiseProgressDTO> batchProgressList = batchProgressService.findBatchwiseProgress();

        // Then
        assertTrue(batchProgressList.isEmpty());
    }
    ////
   

///

@Test
void testCalculateBatchProgressSuccess() {
    // Given
    long batchId = 1L;
    double expectedBatchProgress = 80.0; // Example progress value

    // Mock the behavior of batchProgressRepository.findOverallBatchProgress(batchId)
    // to return a list of results containing the progress for the specified batch ID
    List<Object[]> sampleResults = new ArrayList<>();
    sampleResults.add(new Object[] { expectedBatchProgress }); // Wrap the progress value in an object array
    when(batchProgressRepository.findOverallBatchProgress(batchId)).thenReturn(sampleResults);

    // When
    BatchProgressDTO batchProgressDTO = batchProgressService.calculateBatchProgress(batchId);

    // Then
    assertEquals(batchId, batchProgressDTO.getBatchId());
    assertEquals(expectedBatchProgress, batchProgressDTO.getBatchProgress());
}

///


    @Test
    public void testCalculateOverallBatchProgressAllUsers_Success() {
        // Mock findAllUsers to return sample users
        Long batchId = 1L;
        List<Object[]> users = new ArrayList<>();
        users.add(new Object[] { 1L });
        users.add(new Object[] { 2L });
        when(batchProgressRepository.findAllUsers(batchId)).thenReturn(users);

        // Mock calculateOverallProgressForUser for each user ID
        when(userProgressService.calculateOverallProgressForUser(1L)).thenReturn(new UserProgressDTO(1L, 50));
        when(userProgressService.calculateOverallProgressForUser(2L)).thenReturn(new UserProgressDTO(2L, 75));

        // Execute the method under test
        List<UserBatchProgressDTO> result = batchProgressService.calculateOverallBatchProgressAllUsers(batchId);

        // Assert that the result contains two UserBatchProgressDTO objects
        assertEquals(2, result.size());
    }

    @Test
    public void testCalculateOverallBatchProgressAllUsers_BatchIdNotFoundException() {
        // Mock findAllUsers to return an empty list
        Long batchId = 1L;
        when(batchProgressRepository.findAllUsers(batchId)).thenReturn(new ArrayList<>());

        // Execute the method under test and expect BatchIdNotFoundException
        assertThrows(BatchIdNotFoundException.class, () -> {
            batchProgressService.calculateOverallBatchProgressAllUsers(batchId);
        });
    }




}
