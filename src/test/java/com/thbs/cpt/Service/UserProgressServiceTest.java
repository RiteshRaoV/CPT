package com.thbs.cpt.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserAllCourseProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserResourceProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Entity.Progress;
import com.thbs.cpt.Repository.ProgressRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserProgressServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private UserProgressService userProgressService;

    @Test
    void testCalculateOverallProgressForUser() {
        // Given
        long userId = 1L;
        double expectedOverallProgress = 80.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { userId, expectedOverallProgress });
        when(progressRepository.findOverallProgressForUser(anyLong())).thenReturn(sampleData);

        // When
        UserProgressDTO result = userProgressService.calculateOverallProgressForUser(userId);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(expectedOverallProgress, result.getOverallProgress());
    }

    @Test
    void testCalculateCourseProgressForUser() {
        // Given
        long userId = 1L;
        int courseId = 1;
        double expectedCourseProgress = 70.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { courseId, userId, expectedCourseProgress });
        when(progressRepository.findCourseProgressByUserAndCourse(anyLong(), anyInt())).thenReturn(sampleData);

        // When
        UserCourseProgressDTO result = userProgressService.calculateCourseProgressForUser(userId, courseId);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(expectedCourseProgress, result.getCourseProgress());
    }

    @Test
    void testCalculateUserTopicProgress() {
        // Given
        long userId = 1L;
        int courseId = 1;
        int topicId = 1;
        double expectedTopicProgress = 75.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { userId, courseId, topicId, expectedTopicProgress });
        when(progressRepository.findTopicProgressByCourseAndUserId(anyLong(), anyInt(), anyInt())).thenReturn(sampleData);

        // When
        UserTopicProgressDTO result = userProgressService.calculateUserTopicProgress(userId, courseId, topicId);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(expectedTopicProgress, result.getTopicProgress());
    }


//// batch test case
@Test
    void testCalculateBatchProgress() {
        // Given
        int batchId = 1;
        double expectedBatchProgress = 50.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { expectedBatchProgress });
        when(progressRepository.findOverallBatchProgress(batchId)).thenReturn(sampleData);

        // When
        BatchProgressDTO result = userProgressService.calculateBatchProgress(batchId);

        // Then
        assertEquals(batchId, result.getBatchId());
        assertEquals(expectedBatchProgress, result.getBatchProgress());
    }
    /// resouce id test cases
    @Test
void testCalculateResourceProgressForUser() {
    // Given
    long userId = 1L;
    int resourceId = 1;
    double expectedResourceProgress = 50.0;

    // Mock the repository method call to return sample data
    when(progressRepository.findByUserIdAndResourceId(userId, resourceId)).thenReturn(new Progress(userId, resourceId, expectedResourceProgress));

    // When
    UserResourceProgressDTO result = userProgressService.calculateResourceProgressForUser(userId, resourceId);

    // Then
    assertEquals(userId, result.getUserId());
    assertEquals(expectedResourceProgress, result.getResourceProgress());
}

/// post 
@Test
void testCalculateCourseProgressForUserq() {
    // Given
    long userId = 1L;
    List<Integer> courseIds = Arrays.asList(1, 2, 3);
    
    // Sample data
    List<Object[]> sampleData = new ArrayList<>();
    sampleData.add(new Object[] { userId, 1, 70.0 });
    sampleData.add(new Object[] { userId, 2, 80.0 });
    sampleData.add(new Object[] { userId, 3, 90.0 });
    
    // Mock the repository method call to return sample data
    when(progressRepository.findCourseProgressByUserAndCourses(userId, courseIds)).thenReturn(sampleData);
    
    // When
    List<UserAllCourseProgressDTO> result = userProgressService.calculateCourseProgressForUser(userId, courseIds);
    
    // Then
    assertEquals(3, result.size());
    
    // Check the values of each course progress DTO
    assertEquals(userId, result.get(0).getUserId());
    assertEquals(1, result.get(0).getCourseId());
    assertEquals(70.0, result.get(0).getOverallProgress());
    
    assertEquals(userId, result.get(1).getUserId());
    assertEquals(2, result.get(1).getCourseId());
    assertEquals(80.0, result.get(1).getOverallProgress());
    
    assertEquals(userId, result.get(2).getUserId());
    assertEquals(3, result.get(2).getCourseId());
    assertEquals(90.0, result.get(2).getOverallProgress());
}

@Test
void testCalculateCourseProgressForUser_EmptyResult() {
    // Given
    long userId = 1L;
    List<Integer> courseIds = Arrays.asList(1, 2, 3);
    
    // Mock the repository method call to return an empty list
    when(progressRepository.findCourseProgressByUserAndCourses(userId, courseIds)).thenReturn(Collections.emptyList());
    
    // When
    List<UserAllCourseProgressDTO> result = userProgressService.calculateCourseProgressForUser(userId, courseIds);
    
    // Then
    assertEquals(0, result.size());
}
  

/// batch waise

@Test
    void testFindBatchwiseProgress() {
        // Given
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[] { 1, 90.0 });
        mockResults.add(new Object[] { 2, 85.0 });
        when(progressRepository.findBatchwiseProgress()).thenReturn(mockResults);

        List<BatchWiseProgressDTO> expectedProgressList = new ArrayList<>();
        expectedProgressList.add(new BatchWiseProgressDTO(1, 90.0));
        expectedProgressList.add(new BatchWiseProgressDTO(2, 85.0));

        // When
        List<BatchWiseProgressDTO> actualProgressList = userProgressService.findBatchwiseProgress();

        // Then
        assertEquals(expectedProgressList.size(), actualProgressList.size());
        for (int i = 0; i < expectedProgressList.size(); i++) {
            BatchWiseProgressDTO expectedProgress = expectedProgressList.get(i);
            BatchWiseProgressDTO actualProgress = actualProgressList.get(i);
            assertEquals(expectedProgress.getBatchId(), actualProgress.getBatchId());
            assertEquals(expectedProgress.getBatchProgress(), actualProgress.getBatchProgress());
        }
    }



}
