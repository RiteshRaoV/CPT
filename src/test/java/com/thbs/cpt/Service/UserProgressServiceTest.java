package com.thbs.cpt.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.thbs.cpt.DTO.UserAllCourseProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserResourceProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Entity.Progress;
import com.thbs.cpt.Exception.CourseNotFoundException;
import com.thbs.cpt.Exception.ResourceIdNotFoundException;
import com.thbs.cpt.Exception.TopicIdNotFoundException;
import com.thbs.cpt.Exception.UserNotFoundException;
import com.thbs.cpt.Repository.ProgressRepository;

@SpringBootTest
public class UserProgressServiceTest {

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private UserProgressService userProgressService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateOverallProgressForUser_userFound() throws UserNotFoundException {
        // Arrange
        long userId = 1L;
        double expectedOverallProgress = 0.85;
        Object[] result = { userId, expectedOverallProgress };
        when(progressRepository.findOverallProgressForUser(userId)).thenReturn(Collections.singletonList(result));

        // Act
        UserProgressDTO userProgressDTO = userProgressService.calculateOverallProgressForUser(userId);

        // Assert
        assertEquals(userId, userProgressDTO.getUserId());
        assertEquals(expectedOverallProgress, userProgressDTO.getOverallProgress());
    }

    @Test
    public void testCalculateOverallProgressForUser_userNotFound() {
        // Arrange
        long userId = 1L;
        when(progressRepository.findOverallProgressForUser(userId)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userProgressService.calculateOverallProgressForUser(userId);
        });
    }

    // -------------------course-test-cases----------------------------

    @Test
    public void testCalculateCourseProgressForUser_courseProgressFound()
            throws UserNotFoundException, CourseNotFoundException {
        // Arrange
        long userId = 1L;
        int courseId = 1;
        double expectedCourseProgress = 0.75;
        Object[] result = { userId, courseId, expectedCourseProgress };
        when(progressRepository.findCourseProgressByUserAndCourse(userId, courseId))
                .thenReturn(Collections.singletonList(result));

        // Act
        UserCourseProgressDTO userCourseProgressDTO = userProgressService.calculateCourseProgressForUser(userId,
                courseId);

        // Assert
        assertEquals(userId, userCourseProgressDTO.getUserId());
        assertEquals(expectedCourseProgress, userCourseProgressDTO.getCourseProgress());
    }

    @Test
    public void testCalculateCourseProgressForUser_courseProgressNotFound() {
        // Arrange
        long userId = 1L;
        int courseId = 1;
        when(progressRepository.findCourseProgressByUserAndCourse(userId, courseId))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userProgressService.calculateCourseProgressForUser(userId, courseId);
        });
    }

    @Test
    public void testCalculateCourseProgressForUser_userNotFound() {
        // Arrange
        long userId = 1L;
        int courseId = 1;
        Object[] result = null;
        when(progressRepository.findCourseProgressByUserAndCourse(userId, courseId))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userProgressService.calculateCourseProgressForUser(userId, courseId);
        });
    }

    // --------------------topic-test-cases---------------------------------------

    @Test
    public void testCalculateUserTopicProgress_topicProgressFound()
            throws UserNotFoundException, CourseNotFoundException, TopicIdNotFoundException {
        // Arrange
        long userId = 1L;
        int courseId = 1;
        int topicId = 1;
        double expectedTopicProgress = 0.75;
        Object[] result = { userId, courseId, topicId, expectedTopicProgress };
        when(progressRepository.findTopicProgressByCourseAndUserId(userId, courseId, topicId))
                .thenReturn(Collections.singletonList(result));

        // Act
        UserTopicProgressDTO userTopicProgressDTO = userProgressService.calculateUserTopicProgress(userId, courseId,
                topicId);

        // Assert
        assertEquals(userId, userTopicProgressDTO.getUserId());
        assertEquals(expectedTopicProgress, userTopicProgressDTO.getTopicProgress());
    }

    @Test
    public void testCalculateUserTopicProgress_topicProgressNotFound() {
        // Arrange
        long userId = 1L;
        int courseId = 1;
        int topicId = 1;
        when(progressRepository.findTopicProgressByCourseAndUserId(userId, courseId, topicId))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(TopicIdNotFoundException.class, () -> {
            userProgressService.calculateUserTopicProgress(userId, courseId, topicId);
        });
    }

    // ---------------------batch-test-cases-------------------------------------------

    @Test
    void testCalculateResourceProgressForUser_WhenProgressExists_ReturnsUserResourceProgressDTO() {
        // Arrange
        long userId = 1;
        int resourceId = 1;
        double completionPercentage = 75.0;
        Progress progress = new Progress(userId, resourceId, completionPercentage);
        when(progressRepository.findByUserIdAndResourceId(userId, resourceId)).thenReturn(progress);

        // Act
        UserResourceProgressDTO userResourceProgressDTO = userProgressService.calculateResourceProgressForUser(userId,
                resourceId);

        // Assert
        assertNotNull(userResourceProgressDTO);
        assertEquals(userId, userResourceProgressDTO.getUserId());
        assertEquals(completionPercentage, userResourceProgressDTO.getResourceProgress());
    }

    @Test
    void testCalculateResourceProgressForUser_WhenProgressDoesNotExist_ThrowsException() {
        // Arrange
        long userId = 1;
        int nonExistentResourceId = 999;
        when(progressRepository.findByUserIdAndResourceId(userId, nonExistentResourceId)).thenReturn(null);

        // Act and Assert
        ResourceIdNotFoundException exception = assertThrows(ResourceIdNotFoundException.class, () -> {
            userProgressService.calculateResourceProgressForUser(userId, nonExistentResourceId);
        });

        assertEquals("Resource with ID 999 not found for user 1", exception.getMessage());
    }

    @Test
    void testCalculateCourseProgressForUser_WhenResultsExist_ReturnsUserAllCourseProgressDTOList() {
        // Arrange
        Long userId = 1L;
        List<Long> courseIds = List.of(1L, 2L);
        Object[] result1 = { userId, 1, 45.83333333333333 };
        Object[] result2 = { userId, 2, 87.5 };
        List<Object[]> results = List.of(result1, result2);
        when(progressRepository.findCourseProgressByUserAndCourses(userId, courseIds)).thenReturn(results);

        // Act
        List<UserAllCourseProgressDTO> progressList = userProgressService.calculateCourseProgressForUser(userId,
                courseIds);

        // Assert
        assertNotNull(progressList);
        assertEquals(2, progressList.size());
        assertEquals(userId, progressList.get(0).getUserId());
        assertEquals(1, progressList.get(0).getCourseId()); // Corrected courseId assertion
        assertEquals(45.83333333333333, progressList.get(0).getOverallProgress());
        assertEquals(userId, progressList.get(1).getUserId());
        assertEquals(2, progressList.get(1).getCourseId()); // Corrected courseId assertion
        assertEquals(87.5, progressList.get(1).getOverallProgress());
    }

    @Test
    void testCalculateCourseProgressForUser_WhenResultsDoNotExist_ReturnsEmptyList() {
        // Arrange
        Long userId = 1L;
        List<Long> courseIds = List.of(1L, 2L, 3L);
        when(progressRepository.findCourseProgressByUserAndCourses(userId, courseIds))
                .thenReturn(Collections.emptyList());

        // Act
        List<UserAllCourseProgressDTO> progressList = userProgressService.calculateCourseProgressForUser(userId,
                courseIds);

        // Assert
        assertNotNull(progressList);
        assertTrue(progressList.isEmpty());
    }

}
