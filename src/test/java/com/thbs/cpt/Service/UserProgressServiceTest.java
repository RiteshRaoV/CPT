package com.thbs.cpt.Service;
import com.thbs.cpt.Controller.UserProgressController;
import com.thbs.cpt.DTO.*;
import com.thbs.cpt.Entity.Progress;
import com.thbs.cpt.Exception.*;
import com.thbs.cpt.Repository.ProgressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProgressServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private UserProgressService userProgressService;
    @InjectMocks
    private UserProgressController userProgressController;

    @Test
    void testCalculateOverallProgressForUserSuccess() throws UserNotFoundException {
        long userId = 1L;
        long batchId = 1L;
        Object[] result = new Object[]{userId, 0.75}; // Assuming overall progress is 75%
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(progressRepository.findOverallProgressForUser(userId,batchId)).thenReturn(results);

        UserProgressDTO dto = userProgressService.calculateOverallProgressForUser(userId,batchId);

        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(0.75, dto.getOverallProgress());
    }

    @Test
    void testCalculateOverallProgressForUserUserNotFound() {
        long userId = 999L;
        long batchId = 1L;
        when(progressRepository.findOverallProgressForUser(userId,batchId)).thenReturn(new ArrayList<>());

        assertThrows(UserNotFoundException.class, () -> userProgressService.calculateOverallProgressForUser(userId,batchId));
    }
   ////
   @Test
    void testCalculateCourseProgressForUser_Success() throws CourseNotFoundException {
        // Given
        Long userId = 1L;
        long courseId = 1L;
        long batchId=1L;
        Object[] mockResult = {userId, 1, 70.0}; // Sample result from the repository
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(mockResult);
        when(progressRepository.findCourseProgressByUserAndCourse(userId,batchId, courseId)).thenReturn(mockResults);

        // When
        UserCourseProgressDTO result = userProgressService.calculateCourseProgressForUser(userId,batchId, courseId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(70.0, result.getCourseProgress());
    }

    @Test
    void testCalculateCourseProgressForUser_CourseNotFoundException() {
        // Given
        Long userId = 1L;
        long courseId = 1L;
        long batchId = 1L;

        when(progressRepository.findCourseProgressByUserAndCourse(userId,batchId, courseId)).thenReturn(new ArrayList<>());

        // When and Then
        assertThrows(CourseNotFoundException.class, () -> userProgressService.calculateCourseProgressForUser(userId,batchId, courseId));
    }

    @Test
    void testCalculateUserTopicProgressSuccess() throws TopicIdNotFoundException {
        long userId = 1L;
        long topicId = 1L;
        long batchId = 1L;

        Object[] result = new Object[]{userId, topicId, 0.75}; // Assuming topic progress is 75%
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(progressRepository.findTopicProgressByTopicAndUserId(userId,batchId, topicId)).thenReturn(results);
    
        UserTopicProgressDTO dto = userProgressService.calculateUserTopicProgress(userId,batchId, topicId);
    
        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(0.75, dto.getTopicProgress());
    }
    

    @Test
    void testCalculateUserTopicProgressTopicNotFound() {
        long userId = 1L;
        long topicId = 999L;
        long batchId = 1L;

        when(progressRepository.findTopicProgressByTopicAndUserId(userId,batchId, topicId)).thenReturn(new ArrayList<>());

        assertThrows(TopicIdNotFoundException.class, () -> userProgressService.calculateUserTopicProgress(userId,batchId, topicId));
    }

    @Test
    void testCalculateResourceProgressForUserSuccess() throws UserNotFoundException, ResourceIdNotFoundException {
        long userId = 1L;
        long resourceId = 1L;
        Progress progress = new Progress();
        progress.setCompletionPercentage(0.5); // Assuming completion percentage is 50%
        when(progressRepository.findByUserIdAndResourceId(userId, resourceId)).thenReturn(progress);

        UserResourceProgressDTO dto = userProgressService.calculateResourceProgressForUser(userId, resourceId);

        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(0.5, dto.getResourceProgress());
    }

    @Test
    void testCalculateResourceProgressForUserResourceNotFound() {
        long userId = 1L;
        long resourceId = 999L;
        when(progressRepository.findByUserIdAndResourceId(userId, resourceId)).thenReturn(null);

        assertThrows(ResourceIdNotFoundException.class, () -> userProgressService.calculateResourceProgressForUser(userId, resourceId));
    }

    @Test
    void testUpdateProgress() throws UserNotFoundException, ResourceIdNotFoundException {
        long userId = 1L;
        long resourceId = 1L;
        double resourceProgress = 0.8;
        Progress progress = new Progress();
        progress.setCompletionPercentage(0.5); // Initial completion percentage is 50%
        when(progressRepository.findByUserIdAndResourceId(userId, resourceId)).thenReturn(progress);

        userProgressService.updateProgress(userId, resourceProgress, resourceId);

        assertEquals(resourceProgress, progress.getCompletionPercentage());
        verify(progressRepository, times(1)).save(progress);
    }
    


    ////// user progress test 


    @Test
void testGetUserProgress_CourseNotFoundException() {
    // Mocking repository response
    Long userId = 1L;
    long batchId = 1L;

    List<Long> courseIds = Arrays.asList(1L, 2L);
    
    // Set up the mock behavior to throw CourseNotFoundException for courseId 1
    when(progressRepository.getUserProgress(userId,batchId, courseIds))
            .thenThrow(new CourseNotFoundException("course with ID 1 not found"));

    // Calling the service method and expecting an exception
    assertThrows(CourseNotFoundException.class,
            () -> userProgressService.getUserProgress(userId,batchId, courseIds));
}


   


}

