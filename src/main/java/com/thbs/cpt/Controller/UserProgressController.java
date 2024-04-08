package com.thbs.cpt.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.CourseIdsRequest;
import com.thbs.cpt.DTO.UserAllCourseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserResourceProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Service.UserProgressService;

@RestController
@RequestMapping("/progress")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<UserCourseProgressDTO> calculateOverallCourseProgress(@PathVariable long userId,
            @PathVariable int CourseId) {
        UserCourseProgressDTO progress = userProgressService.calculateCourseProgressForUser(userId, CourseId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/course/{CourseId}/topic/{TopicId}")
    public ResponseEntity<UserTopicProgressDTO> calculateOverallTopicProgress(@PathVariable long userId,
            @PathVariable int CourseId, @PathVariable int TopicId) {
        UserTopicProgressDTO progress = userProgressService.calculateUserTopicProgress(userId, CourseId, TopicId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/resource/{resourceId}")
    public ResponseEntity<UserResourceProgressDTO> calculateResourceProgress(@PathVariable int resourceId,
            @PathVariable long userId) {
        UserResourceProgressDTO progress = userProgressService.calculateResourceProgressForUser(userId, resourceId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<BatchProgressDTO> calculateBatchProgress(@PathVariable int batchId) {
        BatchProgressDTO progress = userProgressService.calculateBatchProgress(batchId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/batchwise")
    public ResponseEntity<List<BatchWiseProgressDTO>> calculateBatchwiseProgress() {
        List<BatchWiseProgressDTO> batchProgressList = userProgressService.findBatchwiseProgress();
        if (!batchProgressList.isEmpty()) {
            return ResponseEntity.ok(batchProgressList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

<<<<<<< HEAD
    @GetMapping("/allusers/{batchId}")
    public ResponseEntity<List<UserBatchProgressDTO>> getOverallBatchProgress(@PathVariable Long batchId) {
        List<UserBatchProgressDTO> progressList = userProgressService.calculateOverallBatchProgress(batchId);
        if (!progressList.isEmpty()) {
            return ResponseEntity.ok(progressList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
=======
   
>>>>>>> 42c1e0935abb7663148af9c7cb691573e8b77fa9

    @PostMapping("/courses")
    public ResponseEntity<List<UserAllCourseProgressDTO>> calculateOverallCourseProgress(
            @RequestBody CourseIdsRequest request) {
        long userId = request.getUserId(); // Get userId from request body
        List<Integer> courseIds = request.getCourseIds();
        List<UserAllCourseProgressDTO> progressList = userProgressService.calculateCourseProgressForUser(userId,
                courseIds);
        if (!progressList.isEmpty()) {
            return ResponseEntity.ok(progressList);
        } else {
            return ResponseEntity.notFound().build();
        }

    
    }
    
    

}
