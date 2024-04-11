package com.thbs.cpt.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class UserProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public UserProgressDTO calculateOverallProgressForUser(Long userId) throws UserNotFoundException {
        List<Object[]> results = progressRepository.findOverallProgressForUser(userId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[0] != null && result[1] != null) {
                long userIdFromQuery = (long) result[0];
                double overallProgress = (double) result[1];
                return new UserProgressDTO(userIdFromQuery, overallProgress);
            }
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return null;
    }

    public UserCourseProgressDTO calculateCourseProgressForUser(Long userId, int courseId)
            throws UserNotFoundException, CourseNotFoundException {
        List<Object[]> results = progressRepository.findCourseProgressByUserAndCourse(userId, courseId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[1] != null && result[2] != null) {
                Number userIdFromQuery = (Number) result[1];
                Number overallProgress = (Number) result[2];
                return new UserCourseProgressDTO(userIdFromQuery.longValue(), overallProgress.doubleValue());
            }
        }
        if (!results.isEmpty()) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
    }

    public UserTopicProgressDTO calculateUserTopicProgress(Long userId, int courseId, int topicId)
            throws UserNotFoundException, CourseNotFoundException, TopicIdNotFoundException {
        List<Object[]> results = progressRepository.findTopicProgressByCourseAndUserId(userId, courseId, topicId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[1] != null && result[2] != null) {
                long userIdFromQuery = (long) result[0];
                double topicProgress = (double) result[3];
                return new UserTopicProgressDTO(userIdFromQuery, topicProgress);
            }
        }
        throw new TopicIdNotFoundException("Course with ID " + courseId + " not found.");
    }

    public UserResourceProgressDTO calculateResourceProgressForUser(long userId, int resourceId)
            throws UserNotFoundException, ResourceIdNotFoundException {
        Progress progress = progressRepository.findByUserIdAndResourceId(userId, resourceId);
        if (progress != null) {
            return new UserResourceProgressDTO(userId, progress.getCompletionPercentage());
        }
        throw new ResourceIdNotFoundException("Resource with ID " + resourceId + " not found for user " + userId);
    }

    public List<UserAllCourseProgressDTO> calculateCourseProgressForUser(Long userId, List<Integer> courseIds) {
        List<Object[]> results = progressRepository.findCourseProgressByUserAndCourses(userId, courseIds);
        List<UserAllCourseProgressDTO> progressList = new ArrayList<>();
        for (Object[] result : results) {
            if (result[0] != null && result[1] != null && result[2] != null) {
                long userIdFromQuery = (long) result[0];
                int courseId = (int) result[1];
                double overallProgress = (double) result[2];
                progressList.add(new UserAllCourseProgressDTO(userIdFromQuery, courseId, overallProgress));
            }
        }
        return progressList;
    }

}
