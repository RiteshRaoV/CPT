package com.thbs.cpt.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thbs.cpt.Entity.Progress;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
     
     //find overall progress of user
     @Query(value = "SELECT user_id, AVG(course_progress) AS overall_progress " +
               "FROM (" +
               "   SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress " +
               "   FROM Progress p " +
               "   JOIN Resource r ON p.resource_id = r.resource_id " +
               "   JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
               "   WHERE p.user_id = :userId " +
               "   GROUP BY lr.course_id, p.user_id " +
               ") AS cp " +
               "GROUP BY user_id", nativeQuery = true)
     List<Object[]> findOverallProgressForUser(Long userId);

     // Fetches the overall course progress of a user in a course
     @Query(value = "SELECT tp.course_id, tp.user_id, AVG(tp.topic_progress) AS course_progress " +
               "FROM (" +
               "   SELECT lr.course_id, lr.topic_id, p.user_id, AVG(p.completion_percentage) AS topic_progress " +
               "   FROM Progress p " +
               "   JOIN Resource r ON p.resource_id = r.resource_id " +
               "   JOIN Learning_Resource lr ON r.learning_resource_id = lr.learning_resource_id " +
               "   WHERE p.user_id = :userId AND lr.course_id = :courseId " +
               "   GROUP BY lr.course_id, lr.topic_id, p.user_id" +
               ") AS tp " +
               "GROUP BY tp.course_id, tp.user_id", nativeQuery = true)
     List<Object[]> findCourseProgressByUserAndCourse(Long userId, long courseId);

     // Fetches the overall topic progress of a user in a particular topic
     @Query(value = "SELECT" +
               " p.user_id,lr.course_id,lr.topic_id," +
               " AVG(p.completion_percentage) AS progress" +
               " FROM" +
               " progress p" +
               " JOIN" +
               " resource r ON p.resource_id = r.resource_id" +
               " JOIN " +
               " Learning_resource lr ON r.learning_resource_id = lr.learning_resource_id" +
               " WHERE" +
               " p.user_id = :userId " +
               " AND lr.course_id = :courseId " +
               " AND lr.topic_id = :topicId " +
               " GROUP BY " +
               " p.user_id, lr.course_id, lr.topic_id", nativeQuery = true)
     List<Object[]> findTopicProgressByCourseAndUserId(Long userId, long courseId, long topicId);


     // Fetches overall progress of the user in multiple courses
     @Query(value = "SELECT tp.user_id, tp.course_id, AVG(tp.topic_progress) AS course_progress " +
               "FROM (" +
               "   SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS topic_progress " +
               "   FROM Progress p " +
               "   JOIN Resource r ON p.resource_id = r.resource_id " +
               "   JOIN Learning_Resource lr ON r.learning_resource_id = lr.learning_resource_id " +
               "   WHERE p.user_id = :userId AND lr.course_id IN :courseIds " +
               "   GROUP BY lr.course_id, p.user_id" +
               ") AS tp " +
               "GROUP BY tp.user_id, tp.course_id", nativeQuery = true)
     List<Object[]> findCourseProgressByUserAndCourses(@Param("userId") Long userId,
               @Param("courseIds") List<Long> courseIds);

     // Fetches overall progress of the user in multiple courses
     @Query(value = "SELECT "+
      "p.user_id,lr.course_id,lr.topic_id, "+
     " AVG(p.completion_percentage) AS progress "+
     " FROM "+
     " progress p "+
     " JOIN "+
     " resource r ON p.resource_id = r.resource_id "+
     " JOIN  "+
     " Learning_resource lr ON r.learning_resource_id = lr.learning_resource_id "+
     " WHERE "+
     " p.user_id = :userId  "+
     " AND lr.course_id in :courseIds "+
     " GROUP BY "+
     " p.user_id, lr.course_id, lr.topic_id",nativeQuery = true)
     List<Object[]>  getUserProgress(Long userId, List<Long>courseIds);

     // Fetches the progress of a user in a particular resource
     Progress findByUserIdAndResourceId(long userId, long resourceId);

}