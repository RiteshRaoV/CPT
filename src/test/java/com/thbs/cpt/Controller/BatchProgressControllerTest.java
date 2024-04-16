package com.thbs.cpt.Controller;
import com.thbs.cpt.DTO.BUProgressDTO;
import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.Service.BatchProgressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
//
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @SuppressWarnings("deprecation")
    @Test
    void testGetOverallBuProgressNotEmpty() {
        // Sample data
        String buName = "SampleBU";
        List<UserBatchProgressDTO> progressList = new ArrayList<>();
        progressList.add(new UserBatchProgressDTO(1L, 0.75));

        // Stubbing batchProgressService.calculateBuProgress() method
        when(batchProgressService.calculateBuProgress(buName)).thenReturn(progressList);

        // Call the controller method
        ResponseEntity<List<UserBatchProgressDTO>> responseEntity = batchProgressController.getOverallBuProgress(buName);

        // Assertions
        assertEquals(progressList, responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verify that batchProgressService.calculateBuProgress() is called once with the specified BU name
        verify(batchProgressService, times(1)).calculateBuProgress(buName);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetOverallBuProgressEmpty() {
        // Sample data
        String buName = "SampleBU";
        List<UserBatchProgressDTO> emptyProgressList = new ArrayList<>();

        // Stubbing batchProgressService.calculateBuProgress() method to return empty list
        when(batchProgressService.calculateBuProgress(buName)).thenReturn(emptyProgressList);

        // Call the controller method
        ResponseEntity<List<UserBatchProgressDTO>> responseEntity = batchProgressController.getOverallBuProgress(buName);

        // Assertions
        assertEquals(404, responseEntity.getStatusCodeValue());
    }
    /// test bu overall 

    @SuppressWarnings("null")
    @Test
    void testGetOverallBUnitProgress_Success() {
        // Mocking the batchProgressService to return a non-null BUProgressDTO
        BUProgressDTO mockProgress = new BUProgressDTO("TestBU", 75.0);
        when(batchProgressService.findOverallBUProgress("TestBU")).thenReturn(mockProgress);

        // Calling the controller method
        ResponseEntity<BUProgressDTO> response = batchProgressController.getOverallBUnitProgress("TestBU");

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TestBU", response.getBody().getBuName());
        assertEquals(75.0, response.getBody().getOverallProgress());
    }

    @Test
    void testGetOverallBUnitProgress_NotFound() {
        // Mocking the batchProgressService to return null
        when(batchProgressService.findOverallBUProgress("NonExistentBU")).thenReturn(null);

        // Calling the controller method
        ResponseEntity<BUProgressDTO> response = batchProgressController.getOverallBUnitProgress("NonExistentBU");

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

  

    @Test
    void testGetOverallBUnitProgress_InvalidInput() {
        // Calling the controller method with an empty buName
        ResponseEntity<BUProgressDTO> responseEmpty = batchProgressController.getOverallBUnitProgress("");

        // Assertions for empty buName
        assertNotNull(responseEmpty);
      
        assertNull(responseEmpty.getBody());

        // Calling the controller method with null buName
        ResponseEntity<BUProgressDTO> responseNull = batchProgressController.getOverallBUnitProgress(null);

        // Assertions for null buName
        assertNotNull(responseNull);
       
        assertNull(responseNull.getBody());
    }

    @Test
    void testGetOverallBUnitProgress_VerifyServiceCall() {
        // Calling the controller method
        batchProgressController.getOverallBUnitProgress("TestBU");

        // Verifying the batchProgressService method call
        verify(batchProgressService, times(1)).findOverallBUProgress("TestBU");
    }
    

    @Test
    public void testGetCourseProgressOfUsersInBatch_EmptyResponse() {
        // Mocking
        long batchId = 123;
        long courseId = 456;

        when(batchProgressService.calculateCourseProgressOfUsersInBatch(batchId, courseId)).thenReturn(new ArrayList<>());

        // Execution
        ResponseEntity<List<UserCourseProgressDTO>> responseEntity = batchProgressController.getCourseProgressOfUsersInBatch(batchId, courseId);

        // Assertion
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
