package com.thbs.cpt.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thbs.cpt.Service.UserProgressService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProgressRepositoryTest {

    @Mock
    private ProgressRepository progressRepository;

    @InjectMocks
    private UserProgressService userProgressService; // Assuming UserProgressService is your service class that uses ProgressRepository

    @Test
    void testFindOverallProgressForUser() {
        // Given
        long userId = 1L;
        double expectedOverallProgress = 80.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { userId, expectedOverallProgress });
        when(progressRepository.findOverallProgressForUser(anyLong())).thenReturn(sampleData);

        // When
        List<Object[]> actualProgressList = progressRepository.findOverallProgressForUser(userId);

        // Then
        assertEquals(1, actualProgressList.size());
        Object[] result = actualProgressList.get(0);
        assertEquals(userId, result[0]);
        assertEquals(expectedOverallProgress, result[1]);
    }

    @Test
    void testFindCourseProgressByUserAndCourse() {
        // Given
        long userId = 1L;
        int courseId = 1;
        double expectedCourseProgress = 70.0;

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        sampleData.add(new Object[] { courseId, userId, expectedCourseProgress });
        when(progressRepository.findCourseProgressByUserAndCourse(anyLong(), anyInt())).thenReturn(sampleData);

        // When
        List<Object[]> actualProgressList = progressRepository.findCourseProgressByUserAndCourse(userId, courseId);

        // Then
        assertEquals(1, actualProgressList.size());
        Object[] result = actualProgressList.get(0);
        assertEquals(courseId, result[0]);
        assertEquals(userId, result[1]);
        assertEquals(expectedCourseProgress, result[2]);
    }

    @Test
    void testFindTopicProgressByCourseAndUserId() {
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
        List<Object[]> actualProgressList = progressRepository.findTopicProgressByCourseAndUserId(userId, courseId, topicId);

        // Then
        assertEquals(1, actualProgressList.size());
        Object[] result = actualProgressList.get(0);
        assertEquals(userId, result[0]);
        assertEquals(courseId, result[1]);
        assertEquals(topicId, result[2]);
        assertEquals(expectedTopicProgress, result[3]);
    }

/// new test cases 



    ///post test 
    @Test
    void testFindCourseProgressByUserAndCourses() {
        // Given
        long userId = 1L;
        List<Long> courseIds = Arrays.asList(1L, 2L);

        // Mock the repository method call to return sample data
        List<Object[]> sampleData = new ArrayList<>();
        // Assuming the expected results for the test scenario
        // For simplicity, let's say we expect 2 course progress records
        // The actual values would depend on your test scenario
        sampleData.add(new Object[] { userId, 1, 60.0 }); // Course 1 progress
        sampleData.add(new Object[] { userId, 2, 75.0 }); // Course 2 progress
        when(progressRepository.findCourseProgressByUserAndCourses(anyLong(), any())).thenReturn(sampleData);

        // When
        List<Object[]> actualProgressList = progressRepository.findCourseProgressByUserAndCourses(userId, courseIds);

        // Then
        assertEquals(2, actualProgressList.size());

        // You may need to adjust the assertions based on your actual test scenario
        Object[] result1 = actualProgressList.get(0);
        assertEquals(userId, result1[0]);
        assertEquals(1, result1[1]);
        assertEquals(60.0, result1[2]);

        Object[] result2 = actualProgressList.get(1);
        assertEquals(userId, result2[0]);
        assertEquals(2, result2[1]);
        assertEquals(75.0, result2[2]);
    }

//// batchwise 




}
