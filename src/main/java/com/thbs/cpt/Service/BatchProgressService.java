package com.thbs.cpt.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thbs.cpt.DTO.BUProgressDTO;
import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.DTO.UserProgressDTO;
import com.thbs.cpt.Exception.BatchIdNotFoundException;
import com.thbs.cpt.Repository.BatchProgressRepository;

import ch.qos.logback.classic.Logger;

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
        List<Object[]> users=batchProgressRepository.findAllUsers(batchId);
        if (users != null && !users.isEmpty()) {
            List<UserBatchProgressDTO> progressList = new ArrayList<>();
            for (Object[] user : users) {
                if (user[0] != null) {
                    Long userId = (Long) user[0];
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

    public List<UserBatchProgressDTO> calculateBuProgress(String buisnessUnit) {
        String uri = "http://172.18.4.185:7001/user/byBusinessUnit/{buisnessUnit}";
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<List<Long>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {}, buisnessUnit);
        List<Long> userIds = response.getBody();
        
        if (userIds != null && !userIds.isEmpty()) {
            List<Object[]> results = batchProgressRepository.findUserProgressInBu(userIds);
            if (!results.isEmpty()) {
                List<UserBatchProgressDTO> userProgressList = new ArrayList<>();
                for (Object[] result : results) {
                    Long userId = (Long) result[0];
                    Double overallProgress = (Double) result[1];
                    userProgressList.add(new UserBatchProgressDTO(userId, overallProgress));
                }
                return userProgressList;
            } else {
                throw new BatchIdNotFoundException("No progress found for users in batch with ID " + buisnessUnit);
            }
        } else {
            throw new BatchIdNotFoundException("No users found for batch with ID " + buisnessUnit);
        }
    }
    
    public BUProgressDTO findOverallBUProgress(String buName) {
        String uri = "http://172.18.4.185:7001/user/byBusinessUnit/{buName}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Long>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Long>>() {}, buName);
        List<Long> userIds = response.getBody();

        if (userIds != null && !userIds.isEmpty()) {
            List<Object[]> result = batchProgressRepository.findOverallBUProgress(userIds);
            if (!result.isEmpty()) {
                Object[] res = result.get(0);
                double progress=(double) res[0];
                return new BUProgressDTO(buName, progress);
            } else {
                throw new BatchIdNotFoundException("No progress found for users in batch with ID " + buName);
            }
        } else {
            throw new BatchIdNotFoundException("No users found for batch with ID " + buName);
        }
    }
}
