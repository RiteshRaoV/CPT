package com.thbs.cpt.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thbs.cpt.DTO.CourseIdsRequest;
import com.thbs.cpt.DTO.UserAllCourseProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserResourceProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Service.UserProgressService;

@SpringBootTest
public class UserProgressControllerTest {

    @Mock
    private UserProgressService userProgressService;

    @InjectMocks
    private UserProgressController userProgressController;

    @Test
    public void testCalculateOverallProgress() {
        long userId = 1;
        UserProgressDTO mockProgress = new UserProgressDTO();
        when(userProgressService.calculateOverallProgressForUser(userId)).thenReturn(mockProgress);

        ResponseEntity<UserProgressDTO> response = userProgressController.calculateOverallProgress(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());
    }

    @Test
    public void testCalculateOverallCourseProgress() {
        long userId = 1;
        int courseId = 1;
        UserCourseProgressDTO mockProgress = new UserCourseProgressDTO();
        when(userProgressService.calculateCourseProgressForUser(userId, courseId)).thenReturn(mockProgress);

        ResponseEntity<UserCourseProgressDTO> response = userProgressController.calculateOverallCourseProgress(userId, courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());
    }

    @Test
    public void testCalculateOverallTopicProgress() {
        long userId = 1;
        int courseId = 1;
        int topicId = 1;
        UserTopicProgressDTO mockProgress = new UserTopicProgressDTO();
        when(userProgressService.calculateUserTopicProgress(userId, courseId, topicId)).thenReturn(mockProgress);

        ResponseEntity<UserTopicProgressDTO> response = userProgressController.calculateOverallTopicProgress(userId, courseId, topicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());
    }

    @Test
    public void testCalculateResourceProgress() {
        long userId = 1;
        int resourceId = 1;
        UserResourceProgressDTO mockProgress = new UserResourceProgressDTO();
        when(userProgressService.calculateResourceProgressForUser(userId, resourceId)).thenReturn(mockProgress);

        ResponseEntity<UserResourceProgressDTO> response = userProgressController.calculateResourceProgress(resourceId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());
    }

    @Test
    public void testCalculateOverallCourseProgressWithCourseIdsRequest() {
        long userId = 1;
        List<Integer> courseIds = new ArrayList<>();
        courseIds.add(1);
        courseIds.add(2);
        CourseIdsRequest request = new CourseIdsRequest(userId, courseIds);
        List<UserAllCourseProgressDTO> mockProgressList = new ArrayList<>();
        when(userProgressService.calculateCourseProgressForUser(userId, courseIds)).thenReturn(mockProgressList);

        ResponseEntity<List<UserAllCourseProgressDTO>> response = userProgressController.calculateOverallCourseProgress(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgressList, response.getBody());
    }
}
