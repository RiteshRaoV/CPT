package com.thbs.cpt.DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class UserTopicRequestDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data
        Long userId = 1L;
        List<Long> topicIds = Arrays.asList(101L, 102L, 103L);

        // Create a UserTopicRequestDTO object using the constructor
        UserTopicRequestDTO userTopicRequestDTO = new UserTopicRequestDTO(userId, topicIds);

        // Check if the constructor sets the fields correctly
        assertEquals(userId, userTopicRequestDTO.getUserId());
        assertEquals(topicIds, userTopicRequestDTO.getTopicIds());
    }

    @Test
    void testSetters() {
        // Create a UserTopicRequestDTO object
        UserTopicRequestDTO userTopicRequestDTO = new UserTopicRequestDTO();

        // Set sample data using setters
        Long userId = 1L;
        List<Long> topicIds = Arrays.asList(101L, 102L, 103L);
        userTopicRequestDTO.setUserId(userId);
        userTopicRequestDTO.setTopicIds(topicIds);

        // Check if setters update the fields correctly
        assertEquals(userId, userTopicRequestDTO.getUserId());
        assertEquals(topicIds, userTopicRequestDTO.getTopicIds());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a UserTopicRequestDTO object using the no-args constructor
        UserTopicRequestDTO userTopicRequestDTO = new UserTopicRequestDTO();

        // Check if the object is not null
        assertNotNull(userTopicRequestDTO);
    }
}
