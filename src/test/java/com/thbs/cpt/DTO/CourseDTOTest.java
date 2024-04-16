package com.thbs.cpt.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CourseDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Create sample data
        long courseId = 12345L;
        double courseProgress = 75.0;
        List<TopicDTO> topics = Arrays.asList(
            new TopicDTO(1L, 10.0),
            new TopicDTO(2L, 20.0),
            new TopicDTO(3L, 30.0)
        );

        // Create a CourseDTO object using the constructor
        CourseDTO courseDTO = new CourseDTO(courseId, courseProgress, topics);

        // Check if the constructor sets the fields correctly
        assertEquals(courseId, courseDTO.getCourseId());
        assertEquals(courseProgress, courseDTO.getCourseProgress());

        // Check if the topics list is not null and has the correct size
        assertNotNull(courseDTO.getTopics());
        assertEquals(topics.size(), courseDTO.getTopics().size());

        // Check if each topic in the list has the correct properties
        for (int i = 0; i < topics.size(); i++) {
            TopicDTO expectedTopic = topics.get(i);
            TopicDTO actualTopic = courseDTO.getTopics().get(i);
            assertEquals(expectedTopic.getTopicId(), actualTopic.getTopicId());
            assertEquals(expectedTopic.getProgress(), actualTopic.getProgress());
        }
    }
}

