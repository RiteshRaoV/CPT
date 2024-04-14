package com.thbs.cpt.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.cpt.DTO.CourseIdsRequest;
import com.thbs.cpt.DTO.ProgressDTO;
import com.thbs.cpt.DTO.ProgressRequest;
import com.thbs.cpt.DTO.UserAllCourseProgressDTO;
import com.thbs.cpt.DTO.UserCourseProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.DTO.UserResourceProgressDTO;
import com.thbs.cpt.DTO.UserTopicProgressDTO;
import com.thbs.cpt.Exception.ResourceIdNotFoundException;
import com.thbs.cpt.Service.UserProgressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user-progress")
@CrossOrigin("*")
@Tag(name = "user Progress", description = "Operations related to user progress")

public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    // gives the overall progress of the user
    @Operation(summary  = "gives the overall progress of the user")
    @GetMapping("/{userId}")
    public ResponseEntity<UserProgressDTO> calculateOverallProgress(@PathVariable long userId) {
        UserProgressDTO progress = userProgressService.calculateOverallProgressForUser(userId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the course progress of the user in a particular course
    @Operation(summary  = "gives the course progress of the user in a particular course")
    @GetMapping("/{userId}/course/{courseId}")
    public ResponseEntity<UserCourseProgressDTO> calculateOverallCourseProgress(@PathVariable long userId,
            @PathVariable int courseId) {
        UserCourseProgressDTO progress = userProgressService.calculateCourseProgressForUser(userId, courseId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the progress of the user in a particular topic
    @Operation(summary  ="gives the progress of the user in a particular topic" )
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

    // gives the progress of a user in a particular resourse
    @Operation(summary  = "gives the progress of a user in a particular resourse")
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


    @PostMapping("/courses")
    public ResponseEntity<List<UserAllCourseProgressDTO>> calculateOverallCourseProgress(
            @RequestBody CourseIdsRequest request) {
        long userId = request.getUserId(); // Get userId from request body
        List<Long> courseIds = request.getCourseIds();
        List<UserAllCourseProgressDTO> progressList = userProgressService.calculateCourseProgressForUser(userId,
                courseIds);
        if (!progressList.isEmpty()) {
            return ResponseEntity.ok(progressList);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // gives the progress of a user in the courses and the topic progress within that course
    @Operation(summary  = "gives the progress of a user in the courses and the topic progress within that course")
    @PostMapping("/course-progress")
    public ProgressDTO getUserProgress(@RequestBody ProgressRequest request) {
        return userProgressService.getUserProgress(request.getUserId(), request.getCourseIds());
    }
    // to update the resource completion percentage of the user
    @Operation(summary = "to update the resource completion percentage of the user")
    @PatchMapping("/{userId}/resource/{resourceId}/update/{progress}")
    public ResponseEntity<?> updateProgress(@PathVariable long userId,@PathVariable long resourceId,@PathVariable double progress){
        try{
            userProgressService.updateProgress(userId, progress, resourceId);
            return ResponseEntity.ok().build();
        }catch(ResourceIdNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}

