package com.thbs.cpt.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.CourseIdsRequest;
import com.thbs.cpt.DTO.UserAllCourseProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserResourceProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Service.UserProgressService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserProgressController.class)
@AutoConfigureMockMvc
public class UserProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProgressService userProgressService;

    @InjectMocks
    private UserProgressController userProgressController;

    @Test
    void testCalculateOverallProgress_Success() throws Exception {
        // Given
        long userId = 1L;
        UserProgressDTO expectedProgress = new UserProgressDTO(userId, 80.0);
        when(userProgressService.calculateOverallProgressForUser(userId)).thenReturn(expectedProgress);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        UserTopicProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), UserTopicProgressDTO.class);
        assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
        assertEquals(expectedProgress.getTopicProgress(), actualProgress.getTopicProgress());
    }
    /// test case for resouce 
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        UserResourceProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), UserResourceProgressDTO.class);
        assertEquals(expectedProgress.getUserId(), actualProgress.getUserId());
        assertEquals(expectedProgress.getResourceProgress(), actualProgress.getResourceProgress());
    }
    /// batch progress test cases
    @Test
    void testCalculateBatchProgress_Success() throws Exception {
        // Given
        int batchId = 1;
        BatchProgressDTO expectedProgress = new BatchProgressDTO(batchId, 90.0);
        when(userProgressService.calculateBatchProgress(batchId)).thenReturn(expectedProgress);

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/progress/batch/{batchId}", batchId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Then
        ObjectMapper objectMapper = new ObjectMapper();
        BatchProgressDTO actualProgress = objectMapper.readValue(result.getResponse().getContentAsString(), BatchProgressDTO.class);
        assertEquals(expectedProgress.getBatchId(), actualProgress.getBatchId());
        assertEquals(expectedProgress.getBatchProgress(), actualProgress.getBatchProgress());
    }
    /// post test
    @Test
    void testCalculateOverallCourseProgress_Successq() throws Exception {
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
                .andExpect(MockMvcResultMatchers.status().isOk())
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
