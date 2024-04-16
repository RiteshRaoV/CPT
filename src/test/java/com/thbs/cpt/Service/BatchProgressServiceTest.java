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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

        // Mock the behavior of
        // batchProgressRepository.findOverallBatchProgress(batchId)
        // to return a list of results containing the progress for the specified batch
        // ID
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

    // @Test
    // public void testCalculateBuProgress_WithValidBusinessUnit() throws
    // BatchIdNotFoundException {
    // // Mock RestTemplate and batchProgressRepository

    // List<Long> mockUserIds = Arrays.asList(1L, 2L);
    // List<Object[]> mockResults = Arrays.asList(
    // new Object[]{1L, 0.75},
    // new Object[]{2L, 0.50} // Another user and progress
    // );

    // RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
    // Mockito.when(mockRestTemplate.exchange(anyString(), any(HttpMethod.class),
    // any(HttpEntity.class), any(ParameterizedTypeReference.class), anyString()))
    // .thenReturn(new ResponseEntity<>(mockUserIds, HttpStatus.OK));

    // BatchProgressRepository mockRepository =
    // Mockito.mock(BatchProgressRepository.class);
    // Mockito.when(mockRepository.findUserProgressInBu(mockUserIds)).thenReturn(mockResults);

    // // Call the method
    // String businessUnit = "CDEC";
    // List<UserBatchProgressDTO> result =
    // batchProgressService.calculateBuProgress(businessUnit);

    // // Assertions
    // assertEquals(1, result.size());
    // assertEquals(1L, result.get(0).getUserId());
    // assertEquals(0.75, result.get(0).getOverallProgress());
    // verify(mockRestTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET),
    // any(HttpEntity.class), any(ParameterizedTypeReference.class),
    // eq(businessUnit));
    // verify(mockRepository, times(1)).findUserProgressInBu(mockUserIds);
    // }

    // @Test
    // public void testCalculateBuProgress_WithNoUsers() throws
    // BatchIdNotFoundException {
    // // Mock RestTemplate to return empty user list

    // List<Long> mockUserIds = Collections.emptyList();

    // RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
    // Mockito.when(mockRestTemplate.exchange(anyString(), any(HttpMethod.class),
    // any(HttpEntity.class), any(ParameterizedTypeReference.class), anyString()))
    // .thenReturn(new ResponseEntity<>(mockUserIds, HttpStatus.OK));

    // // No need to mock repository as empty userIds will trigger exception

    // // Call the method
    // String businessUnit = "CDEC";
    // batchProgressService.calculateBuProgress(businessUnit);
    // }

    // @Test
    // public void testCalculateBuProgress_WithNoProgressData() throws
    // BatchIdNotFoundException {
    // // Mock RestTemplate and return user IDs

    // List<Long> mockUserIds = Arrays.asList(1L, 2L);

    // RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
    // Mockito.when(mockRestTemplate.exchange(anyString(), any(HttpMethod.class),
    // any(HttpEntity.class), any(ParameterizedTypeReference.class), anyString()))
    // .thenReturn(new ResponseEntity<>(mockUserIds, HttpStatus.OK));

    // BatchProgressRepository mockRepository =
    // Mockito.mock(BatchProgressRepository.class);
    // Mockito.when(mockRepository.findUserProgressInBu(mockUserIds)).thenReturn(Collections.emptyList());

    // // Call the method
    // String businessUnit = "CDEC";
    // batchProgressService.calculateBuProgress(businessUnit);
    // }

    // @Test
    // public void testCalculateBuProgress_WithRestTemplateError() throws
    // BatchIdNotFoundException {
    // // Mock RestTemplate to throw exception

    // RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
    // Mockito.when(mockRestTemplate.exchange(anyString(), any(HttpMethod.class),
    // any(HttpEntity.class), any(ParameterizedTypeReference.class), anyString()))
    // .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

    // No need to mock repository

    // // Call the method
    // String businessUnit = "CDEC";
    // batchProgressService.calculateBuProgress(businessUnit);
    // }

    @Test
    void testCalculateBuProgress() {
        // Mocking the RestTemplate
        RestTemplate restTemplate = mock(RestTemplate.class);

        // Mocking batchProgressRepository.findUserProgressInBu to return some results
        List<Object[]> sampleResults = Arrays.asList(new Object[] { 1L, 0.8 }, new Object[] { 2L, 0.9 });
        when(batchProgressRepository.findUserProgressInBu(anyList())).thenReturn(sampleResults);

        // Call the method under test
        List<UserBatchProgressDTO> userProgressList = batchProgressService.calculateBuProgress("CDEC");

        // Assertions
        assertEquals(2, userProgressList.size());
        assertEquals(1L, userProgressList.get(0).getUserId());
        assertEquals(0.8, userProgressList.get(0).getOverallProgress());
        assertEquals(2L, userProgressList.get(1).getUserId());
        assertEquals(0.9, userProgressList.get(1).getOverallProgress());
    }

    @Test
    void testCalculateBuProgressNoUsers() {
        // Mocking the RestTemplate
        RestTemplate restTemplate = mock(RestTemplate.class);

        // Call the method under test
        assertThrows(BatchIdNotFoundException.class, () -> batchProgressService.calculateBuProgress("CDEC"));
    }

    @Test
    void testCalculateBuProgressEmptyResults() {

        when(batchProgressRepository.findUserProgressInBu(anyList())).thenReturn(new ArrayList<>());

        assertThrows(BatchIdNotFoundException.class, () -> batchProgressService.calculateBuProgress("CDEC"));
    }

    @Test
    public void testCalculateBuProgress_WithRestTemplateError() throws BatchIdNotFoundException {
        // Mock RestTemplate to throw exception
        RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
        Mockito.lenient().when(mockRestTemplate.exchange(anyString(), any(HttpMethod.class), 
                any(HttpEntity.class), any(ParameterizedTypeReference.class), anyString()))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        
        
        assertThrows(BatchIdNotFoundException.class, () -> batchProgressService.calculateBuProgress("CDEC"));
    }
    
}
