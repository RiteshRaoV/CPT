package com.thbs.cpt.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
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
}
