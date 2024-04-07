package com.thbs.cpt.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
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
}
