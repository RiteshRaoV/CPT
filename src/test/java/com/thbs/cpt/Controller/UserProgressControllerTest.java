package com.thbs.cpt.Controller;

import com.thbs.cpt.DTO.*;
import com.thbs.cpt.Exception.ResourceIdNotFoundException;
import com.thbs.cpt.Service.UserProgressService;
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

@ExtendWith(MockitoExtension.class)
class UserProgressControllerTest {

    @Mock
    private UserProgressService userProgressService;

    @InjectMocks
    private UserProgressController userProgressController;

    @Test
    void testCalculateOverallProgressFound() {
        long userId = 123;
        UserProgressDTO mockProgress = new UserProgressDTO();
        when(userProgressService.calculateOverallProgressForUser(userId)).thenReturn(mockProgress);

        ResponseEntity<UserProgressDTO> response = userProgressController.calculateOverallProgress(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());

        verify(userProgressService, times(1)).calculateOverallProgressForUser(userId);
    }

    @Test
    void testCalculateOverallProgressNotFound() {
        long userId = 456;
        when(userProgressService.calculateOverallProgressForUser(userId)).thenReturn(null);

        ResponseEntity<UserProgressDTO> response = userProgressController.calculateOverallProgress(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userProgressService, times(1)).calculateOverallProgressForUser(userId);
    }

    @Test
    void testCalculateOverallCourseProgressFound() {
        long userId = 123;
        int courseId = 456;
        UserCourseProgressDTO mockProgress = new UserCourseProgressDTO();
        when(userProgressService.calculateCourseProgressForUser(userId, courseId)).thenReturn(mockProgress);

        ResponseEntity<UserCourseProgressDTO> response = userProgressController.calculateOverallCourseProgress(userId,
                courseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());

        verify(userProgressService, times(1)).calculateCourseProgressForUser(userId, courseId);
    }

    @Test
    void testCalculateOverallCourseProgressNotFound() {
        long userId = 789;
        int courseId = 101;
        when(userProgressService.calculateCourseProgressForUser(userId, courseId)).thenReturn(null);

        ResponseEntity<UserCourseProgressDTO> response = userProgressController.calculateOverallCourseProgress(userId,
                courseId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userProgressService, times(1)).calculateCourseProgressForUser(userId, courseId);
    }

    @Test
    void testCalculateOverallTopicProgressFound() {
        long userId = 123;
        int topicId = 456;
        UserTopicProgressDTO mockProgress = new UserTopicProgressDTO();
        when(userProgressService.calculateUserTopicProgress(userId, topicId)).thenReturn(mockProgress);

        ResponseEntity<UserTopicProgressDTO> response = userProgressController.calculateOverallTopicProgress(userId,
                topicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());

        verify(userProgressService, times(1)).calculateUserTopicProgress(userId, topicId);
    }

    @Test
    void testCalculateOverallTopicProgressNotFound() {
        long userId = 789;
        int topicId = 101;
        when(userProgressService.calculateUserTopicProgress(userId, topicId)).thenReturn(null);

        ResponseEntity<UserTopicProgressDTO> response = userProgressController.calculateOverallTopicProgress(userId,
                topicId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userProgressService, times(1)).calculateUserTopicProgress(userId, topicId);
    }

    @Test
    void testCalculateResourceProgressFound() {
        long userId = 123;
        int resourceId = 456;
        UserResourceProgressDTO mockProgress = new UserResourceProgressDTO();
        when(userProgressService.calculateResourceProgressForUser(userId, resourceId)).thenReturn(mockProgress);

        ResponseEntity<UserResourceProgressDTO> response = userProgressController.calculateResourceProgress(resourceId,
                userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProgress, response.getBody());

        verify(userProgressService, times(1)).calculateResourceProgressForUser(userId, resourceId);
    }

    @Test
    void testCalculateResourceProgressNotFound() {
        long userId = 789;
        int resourceId = 101;
        when(userProgressService.calculateResourceProgressForUser(userId, resourceId)).thenReturn(null);

        ResponseEntity<UserResourceProgressDTO> response = userProgressController.calculateResourceProgress(resourceId,
                userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userProgressService, times(1)).calculateResourceProgressForUser(userId, resourceId);
    }

    @Test
    void testGetUserProgress() {
        ProgressRequest request = new ProgressRequest();
        request.setUserId(123L);
        request.setCourseIds(Collections.singletonList(456L));

        ProgressDTO mockProgress = new ProgressDTO();
        when(userProgressService.getUserProgress(request.getUserId(), request.getCourseIds())).thenReturn(mockProgress);

        ProgressDTO response = userProgressController.getUserProgress(request);

        assertEquals(mockProgress, response);

        verify(userProgressService, times(1)).getUserProgress(request.getUserId(), request.getCourseIds());
    }

    @Test
    void testUpdateProgressSuccess() throws ResourceIdNotFoundException {
        long userId = 123;
        long resourceId = 456;
        double progress = 0.5;

        ResponseEntity<?> expectedResponse = ResponseEntity.ok().build();
        doNothing().when(userProgressService).updateProgress(userId, progress, resourceId);

        ResponseEntity<?> response = userProgressController.updateProgress(userId, resourceId, progress);

        assertEquals(expectedResponse, response);

        verify(userProgressService, times(1)).updateProgress(userId, progress, resourceId);
    }

    @Test
    void testUpdateProgressResourceNotFound() throws ResourceIdNotFoundException {
        long userId = 123;
        long resourceId = 456;
        double progress = 0.5;

        doThrow(ResourceIdNotFoundException.class).when(userProgressService).updateProgress(userId, progress,
                resourceId);

        ResponseEntity<?> response = userProgressController.updateProgress(userId, resourceId, progress);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(userProgressService, times(1)).updateProgress(userId, progress, resourceId);
    }

    @Test
    void testFindProgressByUserIdAndTopics() {
        // Mocking input data
        UserTopicRequestDTO userTopicRequest = new UserTopicRequestDTO();
        userTopicRequest.setUserId(1L);
        userTopicRequest.setTopicIds(List.of(1L, 2L));

        // Mocking service response
        List<ResourceProgressDTO> expectedProgressList = new ArrayList<>();
        // Populate expectedProgressList with some test data

        when(userProgressService.findProgressByUserIdAndTopics(1L, List.of(1L, 2L))).thenReturn(expectedProgressList);

        // Call the controller method
        List<ResourceProgressDTO> actualProgressList = userProgressController
                .findProgressByUserIdAndTopics(userTopicRequest);

        // Assertions
        assertEquals(expectedProgressList, actualProgressList);
        verify(userProgressService, times(1)).findProgressByUserIdAndTopics(1L, List.of(1L, 2L));
    }
}
