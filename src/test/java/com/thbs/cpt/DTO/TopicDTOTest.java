package com.thbs.cpt.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TopicDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Sample data
        long topicId = 12345L;
        double progress = 75.0;

        // Create a TopicDTO object using the constructor
        TopicDTO topicDTO = new TopicDTO(topicId, progress);

        // Check if the constructor sets the fields correctly
        assertEquals(topicId, topicDTO.getTopicId());
        assertEquals(progress, topicDTO.getProgress());
    }
}

