package com.thbs.cpt.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.Service.BatchProgressService;

@RestController
@RequestMapping("/progress")
public class BatchProgressController {
    @Autowired
    private BatchProgressService batchProgressService;

// -----------working-fine-------------
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<BatchProgressDTO> calculateBatchProgress(@PathVariable int batchId) {
        BatchProgressDTO progress = batchProgressService.calculateBatchProgress(batchId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/batchwise")
    public ResponseEntity<List<BatchWiseProgressDTO>> calculateBatchwiseProgress() {
        List<BatchWiseProgressDTO> batchProgressList = batchProgressService.findBatchwiseProgress();
        if (!batchProgressList.isEmpty()) {
            return ResponseEntity.ok(batchProgressList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allusers/{batchId}")
    public ResponseEntity<List<UserBatchProgressDTO>> getOverallBatchProgress(@PathVariable Long batchId) {
        List<UserBatchProgressDTO> progressList = batchProgressService.calculateOverallBatchProgress(batchId);
        if (!progressList.isEmpty()) {
            return ResponseEntity.ok(progressList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
