package com.thbs.cpt.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thbs.cpt.Entity.Progress;

@Repository
public interface BatchProgressRepository extends JpaRepository<Progress, Long> {

    @Query(value = "SELECT user_id, AVG(course_progress) AS overall_progress" +
            " FROM (" +
            "    SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress" +
            "    FROM Progress p" +
            "    JOIN Resource r ON p.resource_id = r.resource_id" +
            "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id" +
            "    WHERE p.batch_id = :batchId" +
            "    GROUP BY lr.course_id, p.user_id" +
            ") AS cp" +
            "GROUP BY user_id;", nativeQuery = true)
    List<Object[]> findOvreallProgressOfUsersInABatch(int batchId);

    @Query(value = "SELECT AVG(overall_progress) AS average_overall_progress " +
        " FROM ( " +
        " SELECT user_id, AVG(course_progress) AS overall_progress  " +
        " FROM ( " +
        "     SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress  " +
        "     FROM Progress p  " +
        "     JOIN Resource r ON p.resource_id = r.resource_id  " +
        "     JOIN Learning_Resource lr ON r.learning_resource_id = lr.learning_resource_id  " +
        "     WHERE p.batch_id = :batchId " +
        "     GROUP BY lr.course_id, p.user_id " +
        " ) AS cp  " +
        " GROUP BY user_id " +
    " ) AS user_progress",nativeQuery = true)
    List<Object[]> findOverallBatchProgress(int batchId);

    @Query(value = "SELECT batch_id, AVG(overall_progress) AS batch_completion_progress " +
            "FROM (" +
            "    SELECT p.batch_id, p.user_id, AVG(p.completion_percentage) AS overall_progress " +
            "    FROM Progress p " +
            "    JOIN Resource r ON p.resource_id = r.resource_id " +
            "    JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id " +
            "    GROUP BY p.batch_id, p.user_id " +
            ") AS batch_progress " +
            "GROUP BY batch_id", nativeQuery = true)
    List<Object[]> findBatchwiseProgress();

    @Query(value = "SELECT user_id, AVG(course_progress) AS overall_progress  "+
     "FROM ( "+
        " SELECT lr.course_id, p.user_id, AVG(p.completion_percentage) AS course_progress  "+
        " FROM Progress p  "+
        " JOIN Resource r ON p.resource_id = r.resource_id  "+
        " JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  "+
       "  GROUP BY lr.course_id, p.user_id "+
    " ) AS cp  "+
    " WHERE cp.course_id IN ( "+
    "     SELECT lr.course_id  "+
    "     FROM Progress p  "+
    "     JOIN Resource r ON p.resource_id = r.resource_id  "+
    "     JOIN learning_resource lr ON r.learning_resource_id = lr.learning_resource_id  "+
    "     WHERE p.batch_id = :batchId "+
    " ) "+
    " GROUP BY user_id", nativeQuery = true)
    List<Object[]> findOverallBatchProgressAllUsers(Long batchId);


}
