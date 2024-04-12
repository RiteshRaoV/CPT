package com.thbs.cpt.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.Entity.Progress;
import com.thbs.cpt.Exception.BatchIdNotFoundException;
import com.thbs.cpt.Repository.BatchProgressRepository;

@Service
public class BatchProgressService {
    
    @Autowired
    private BatchProgressRepository batchProgressRepository;

    @Autowired
    private UserProgressService userProgressService;

    public List<BatchWiseProgressDTO> findBatchwiseProgress() {
        List<Object[]> batches=batchProgressRepository.findAllBatches();
        List<BatchWiseProgressDTO> batchProgressList = new ArrayList<>();

        for (Object[] result : batches) {
            long batchId = (long) result[0];
            BatchProgressDTO progress=calculateBatchProgress(batchId);
            batchProgressList.add(new BatchWiseProgressDTO(batchId, progress.getBatchProgress()));
        }

        return batchProgressList;
    }

    public List<UserBatchProgressDTO> calculateOverallBatchProgressAllUsers(Long batchId) throws BatchIdNotFoundException {
        // List<Object[]> results = batchProgressRepository.findOverallBatchProgressAllUsers(batchId);
        List<Object[]> users=batchProgressRepository.findAllUsers(batchId);
        if (users != null && !users.isEmpty()) {
            List<UserBatchProgressDTO> progressList = new ArrayList<>();
            for (Object[] user : users) {
                if (user[0] != null) {
                    Long userId = (Long) user[0];
                    // double overallProgress = (Double) result[1];
                    UserProgressDTO progress=userProgressService.calculateOverallProgressForUser(userId);
                    progressList.add(new UserBatchProgressDTO(userId, progress.getOverallProgress()));
                }
            }
            return progressList;
        } else {
            throw new BatchIdNotFoundException("Batch with ID " + batchId + " not found.");
        }
    }

    public BatchProgressDTO calculateBatchProgress(long batchId) throws BatchIdNotFoundException {
        List<Object[]> results = batchProgressRepository.findOverallBatchProgress(batchId);
        if (results != null && !results.isEmpty()) {
            Object[] result = results.get(0);
            if (result != null && result.length > 0) {
                double batchProgress = (double) result[0];
                batchProgress = Math.round(batchProgress * 100.0) / 100.0;
                return new BatchProgressDTO(batchId, batchProgress);
            }
        }
        throw new BatchIdNotFoundException("Batch with ID " + batchId + " not found.");
    }
}
