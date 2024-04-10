package com.thbs.cpt.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thbs.cpt.DTO.*;
import com.thbs.cpt.Service.UserProgressService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserProgressController.class)
@AutoConfigureMockMvc
public class UserProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProgressService userProgressService;

    
    @Test
    void testCalculateOverallProgress_Success() throws Exception {
        // Given
        long userId = 1L;
        UserProgressDTO expectedProgress = new UserProgressDTO(userId, 80.0);
        when(userProgressService.calculateOverallProgressForUser(userId)).thenReturn(expectedProgress);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        UserProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), UserProgressDTO.class);
        assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
        assertEquals(expectedProgress.getOverallProgress(), actualProgress.getOverallProgress());
    }

    @Test
    void testCalculateOverallCourseProgress_Success() throws Exception {
        // Given
        long userId = 1L;
        int courseId = 1;
        UserCourseProgressDTO expectedProgress = new UserCourseProgressDTO(userId, 70.0);
        when(userProgressService.calculateCourseProgressForUser(userId, courseId)).thenReturn(expectedProgress);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/{userId}/course/{courseId}", userId, courseId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        UserCourseProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), UserCourseProgressDTO.class);
        assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
        assertEquals(expectedProgress.getCourseProgress(), actualProgress.getCourseProgress());
    }

    @Test
    void testCalculateOverallTopicProgress_Success() throws Exception {
        // Given
        long userId = 1L;
        int courseId = 1;
        int topicId = 1;
        UserTopicProgressDTO expectedProgress = new UserTopicProgressDTO(userId, 75.0);
        when(userProgressService.calculateUserTopicProgress(userId, courseId, topicId)).thenReturn(expectedProgress);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/{userId}/course/{courseId}/topic/{topicId}", userId, courseId, topicId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        UserTopicProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), UserTopicProgressDTO.class);
        assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
        assertEquals(expectedProgress.getTopicProgress(), actualProgress.getTopicProgress());
    }

    @Test
    void testCalculateResourceProgress_Success() throws Exception {
        // Given
        long userId = 1L;
        int resourceId = 1;
        UserResourceProgressDTO expectedProgress = new UserResourceProgressDTO(userId, 50.0);
        when(userProgressService.calculateResourceProgressForUser(userId, resourceId)).thenReturn(expectedProgress);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/{userId}/resource/{resourceId}", userId, resourceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        UserResourceProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), UserResourceProgressDTO.class);
        assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
        assertEquals(expectedProgress.getResourceProgress(), actualProgress.getResourceProgress());
    }

    @Test
    void testCalculateBatchwiseProgress_Success() throws Exception {
        // Given
        List<BatchWiseProgressDTO> expectedProgressList = new ArrayList<>();
        expectedProgressList.add(new BatchWiseProgressDTO(1, 90.0));
        expectedProgressList.add(new BatchWiseProgressDTO(2, 85.0));
        when(userProgressService.findBatchwiseProgress()).thenReturn(expectedProgressList);
    
        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/batchwise")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    
        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        List<BatchWiseProgressDTO> actualProgressList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<BatchWiseProgressDTO>>() {});
        assertEquals(expectedProgressList.size(), actualProgressList.size());
        for (int i = 0; i < expectedProgressList.size(); i++) {
            BatchWiseProgressDTO expectedProgress = expectedProgressList.get(i);
            BatchWiseProgressDTO actualProgress = actualProgressList.get(i);
            assertEquals(expectedProgress.getBatchId(), actualProgress.getBatchId());
            assertEquals(expectedProgress.getBatchProgress(), actualProgress.getBatchProgress());
        }
    }
    
    
    

    @Test
    void testGetOverallBatchProgress_Success() throws Exception {
        // Given
        long batchId = 1L;
        List<UserBatchProgressDTO> expectedProgressList = new ArrayList<>();
        expectedProgressList.add(new UserBatchProgressDTO(1L, 80.0));
        expectedProgressList.add(new UserBatchProgressDTO(2L, 85.0));
        when(userProgressService.calculateOverallBatchProgress(batchId)).thenReturn(expectedProgressList);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/allusers/{batchId}", batchId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserBatchProgressDTO> actualProgressList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserBatchProgressDTO>>() {});
        assertEquals(expectedProgressList.size(), actualProgressList.size());
        for (int i = 0; i < expectedProgressList.size(); i++) {
            UserBatchProgressDTO expectedProgress = expectedProgressList.get(i);
            UserBatchProgressDTO actualProgress = actualProgressList.get(i);
            assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
            assertEquals(expectedProgress.getOverallProgress(), actualProgress.getOverallProgress());
        }
    }

    @Test
    void testCalculateOverallCourseProgressBatch_Success() throws Exception {
        // Given
        long userId = 1L;
        List<Integer> courseIds = Arrays.asList(1, 2, 3); // Example list of courseIds
        List<UserAllCourseProgressDTO> expectedProgressList = new ArrayList<>();
        for (int courseId : courseIds) {
            UserAllCourseProgressDTO progress = new UserAllCourseProgressDTO(userId, courseId, 80.0); // Example progress for each courseId
            expectedProgressList.add(progress);
        }
        when(userProgressService.calculateCourseProgressForUser(userId, courseIds)).thenReturn(expectedProgressList);

        // When
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(new CourseIdsRequest(userId, courseIds));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/progress/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<UserAllCourseProgressDTO> actualProgressList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserAllCourseProgressDTO>>() {});
        assertEquals(expectedProgressList.size(), actualProgressList.size());
        for (int i = 0; i < expectedProgressList.size(); i++) {
            UserAllCourseProgressDTO expectedProgress = expectedProgressList.get(i);
            UserAllCourseProgressDTO actualProgress = actualProgressList.get(i);
            assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
            assertEquals(expectedProgress.getCourseId(), actualProgress.getCourseId());
            assertEquals(expectedProgress.getOverallProgress(), actualProgress.getOverallProgress());
        }
    }
}
