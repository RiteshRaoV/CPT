package com.thbs.cpt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Service.UserProgressService;

@RestController
@RequestMapping("/progress")
public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProgressDTO> calculateOverallProgress(@PathVariable long userId) {
        UserProgressDTO progress = userProgressService.calculateOverallProgressForUser(userId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/course/{CourseId}")
    public ResponseEntity<UserCourseProgressDTO> calculateOverallCourseProgress(@PathVariable long userId,@PathVariable int CourseId) {
        UserCourseProgressDTO progress = userProgressService.calculateCourseProgressForUser(userId,CourseId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{userId}/course/{CourseId}/topic/{TopicId}")
    public ResponseEntity<UserTopicProgressDTO> calculateOverallTopicProgress(@PathVariable long userId,@PathVariable int CourseId,@PathVariable int TopicId){
        UserTopicProgressDTO progress=userProgressService.calculateUserTopicProgress(userId,CourseId,TopicId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
