package com.thbs.cpt.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thbs.cpt.DTO.CourseDTO;
import com.thbs.cpt.DTO.ProgressDTO;
import com.thbs.cpt.DTO.TopicDTO;
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

    public UserCourseProgressDTO calculateCourseProgressForUser(Long userId, long courseId)
            throws CourseNotFoundException {
        List<Object[]> results = progressRepository.findCourseProgressByUserAndCourse(userId, courseId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[1] != null && result[2] != null) {
                Number userIdFromQuery = (Number) result[1];
                Number overallProgress = (Number) result[2];
                return new UserCourseProgressDTO(userIdFromQuery.longValue(), overallProgress.doubleValue());
            }
        }else{
            throw new CourseNotFoundException("course with ID " + courseId + " not found.");
        }
        return null;
    }

    public UserTopicProgressDTO calculateUserTopicProgress(Long userId, long topicId)
            throws TopicIdNotFoundException {
        List<Object[]> results = progressRepository.findTopicProgressByTopicAndUserId(userId, topicId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result[1] != null && result[2] != null) {
                long userIdFromQuery = (long) result[0];
                double topicProgress = (double) result[2];
                return new UserTopicProgressDTO(userIdFromQuery, topicProgress);
            }
        }
        throw new TopicIdNotFoundException("Topic with ID " + topicId + " not found.");
    }

    public UserResourceProgressDTO calculateResourceProgressForUser(long userId, long resourceId)
            throws  ResourceIdNotFoundException {
        Progress progress = progressRepository.findByUserIdAndResourceId(userId, resourceId);
        if (progress != null) {
            return new UserResourceProgressDTO(userId, progress.getCompletionPercentage());
        }
        throw new ResourceIdNotFoundException("Resource with ID " + resourceId + " not found for user " + userId);
    }


    public ProgressDTO getUserProgress(Long userId, List<Long> courseIds) {
        List<Object[]> results = progressRepository.getUserProgress(userId, courseIds);
    
        ProgressDTO response = new ProgressDTO();
        response.setUserId(userId);
        List<CourseDTO> courses = new ArrayList<>();
    
        for (Long courseId : courseIds) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseId(courseId);
            courseDTO.setTopics(new ArrayList<>());
            courses.add(courseDTO);
        }
    
        for (Object[] result : results) {
            long courseId = (long) result[1];
            long topicId = (long) result[2];
            double progressDouble = (double) result[3];
    
            // Find the corresponding courseDTO for the current courseId
            CourseDTO courseDTO = findCourse(courses, courseId);
            if (courseDTO == null) {
                throw new CourseNotFoundException("course with ID " + courseId + " not found");
            }
    
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setTopicId(topicId);
            topicDTO.setProgress(progressDouble);
            courseDTO.getTopics().add(topicDTO);
        }
    
        response.setCourses(courses);
        return response;
    }
    
    // Helper method to find a courseDTO by courseId
    private CourseDTO findCourse(List<CourseDTO> courses, long courseId) {
        for (CourseDTO courseDTO : courses) {
            if (courseDTO.getCourseId() == courseId) {
                return courseDTO;
            }
        }
        return null;
    }
    
    private CourseDTO findOrCreateCourse(List<CourseDTO> courses, long courseId,long userId) {
        for (CourseDTO courseDTO : courses) {
            if (courseDTO.getCourseId() == courseId) {
                return courseDTO;
            }
        }
        CourseDTO newCourseDTO = new CourseDTO();
        newCourseDTO.setCourseId(courseId);
        newCourseDTO.setTopics(new ArrayList<>());
        UserCourseProgressDTO progress=calculateCourseProgressForUser(userId,courseId);
        newCourseDTO.setCourseProgress(progress.getCourseProgress());
        courses.add(newCourseDTO);
        return newCourseDTO;
    }

    public void updateProgress(long userId,double resourceProgress,long resourceId){
        Progress progress=progressRepository.findByUserIdAndResourceId(userId, resourceId);
        progress.setCompletionPercentage(resourceProgress);
        progressRepository.save(progress);
    }

}
