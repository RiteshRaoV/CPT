package com.thbs.cpt.Service;
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

    @Test
    void testCalculateOverallProgressForUserSuccess() throws UserNotFoundException {
        long userId = 1L;
        Object[] result = new Object[]{userId, 0.75}; // Assuming overall progress is 75%
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(progressRepository.findOverallProgressForUser(userId)).thenReturn(results);

        UserProgressDTO dto = userProgressService.calculateOverallProgressForUser(userId);

        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(0.75, dto.getOverallProgress());
    }

    @Test
    void testCalculateOverallProgressForUserUserNotFound() {
        long userId = 999L;
        when(progressRepository.findOverallProgressForUser(userId)).thenReturn(new ArrayList<>());

        assertThrows(UserNotFoundException.class, () -> userProgressService.calculateOverallProgressForUser(userId));
    }

    @Test
    void testCalculateCourseProgressForUserSuccess() throws UserNotFoundException, CourseNotFoundException {
        long userId = 1L;
        long courseId = 1L;
        Object[] result = new Object[]{userId, userId, 0.75}; // Assuming course progress is 75%
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(progressRepository.findCourseProgressByUserAndCourse(userId, courseId)).thenReturn(results);
    
        UserCourseProgressDTO dto = userProgressService.calculateCourseProgressForUser(userId, courseId);
    
        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(0.75, dto.getCourseProgress());
    }
    

    @Test
    void testCalculateCourseProgressForUserCourseNotFound() {
        long userId = 1L;
        long courseId = 999L;
        when(progressRepository.findCourseProgressByUserAndCourse(userId, courseId)).thenReturn(new ArrayList<>());

        assertThrows(CourseNotFoundException.class, () -> userProgressService.calculateCourseProgressForUser(userId, courseId));
    }

    @Test
    void testCalculateCourseProgressForUserUserNotFound() {
        long userId = 999L;
        long courseId = 1L;
        when(progressRepository.findCourseProgressByUserAndCourse(userId, courseId)).thenReturn(new ArrayList<>());

        assertThrows(UserNotFoundException.class, () -> userProgressService.calculateCourseProgressForUser(userId, courseId));
    }

    @Test
    void testCalculateUserTopicProgressSuccess() throws TopicIdNotFoundException {
        long userId = 1L;
        long topicId = 1L;
        Object[] result = new Object[]{userId, topicId, 0.75}; // Assuming topic progress is 75%
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(progressRepository.findTopicProgressByTopicAndUserId(userId, topicId)).thenReturn(results);
    
        UserTopicProgressDTO dto = userProgressService.calculateUserTopicProgress(userId, topicId);
    
        assertNotNull(dto);
        assertEquals(userId, dto.getUserId());
        assertEquals(0.75, dto.getTopicProgress());
    }
    

    @Test
    void testCalculateUserTopicProgressTopicNotFound() {
        long userId = 1L;
        long topicId = 999L;
        when(progressRepository.findTopicProgressByTopicAndUserId(userId, topicId)).thenReturn(new ArrayList<>());

        assertThrows(TopicIdNotFoundException.class, () -> userProgressService.calculateUserTopicProgress(userId, topicId));
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
}

