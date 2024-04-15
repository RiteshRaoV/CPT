package com.thbs.cpt.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thbs.cpt.DTO.BatchProgressDTO;
import com.thbs.cpt.DTO.BatchWiseProgressDTO;
import com.thbs.cpt.DTO.UserBatchProgressDTO;
import com.thbs.cpt.Service.BatchProgressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/batch-progress")
@CrossOrigin("*")
@Tag(name = "Batch Progress", description = "Operations related to batch progress")
public class BatchProgressController {
    @Autowired
    private BatchProgressService batchProgressService;

    // gives the progress of a particular batch based on the batchId
    @Operation(summary  = "gives the progress of a particular batch based on the batchId")
    @GetMapping("/{batchId}")
    public ResponseEntity<BatchProgressDTO> calculateBatchProgress(@PathVariable int batchId) {
        BatchProgressDTO progress = batchProgressService.calculateBatchProgress(batchId);
        if (progress != null) {
            return ResponseEntity.ok(progress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // gives the overall progress of all the batches
    @Operation(summary  = "gives the overall progress of all the batches")
    @GetMapping
    public ResponseEntity<List<BatchWiseProgressDTO>> calculateBatchwiseProgress() {
        List<BatchWiseProgressDTO> batchProgressList = batchProgressService.findBatchwiseProgress();
        if (!batchProgressList.isEmpty()) {
            return ResponseEntity.ok(batchProgressList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // gives the overall progress of all the users in the batch
    @Operation(summary  = "gives the overall progress of all the users in the batch")
    @GetMapping("/allusers/{batchId}")
    public ResponseEntity<List<UserBatchProgressDTO>> getOverallBatchProgress(@PathVariable Long batchId) {
        List<UserBatchProgressDTO> progressList = batchProgressService.calculateOverallBatchProgressAllUsers(batchId);
        if (!progressList.isEmpty()) {
            return ResponseEntity.ok(progressList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bu-progress/{buName}")
    public ResponseEntity<List<UserBatchProgressDTO>> getOverallBuProgress(@PathVariable String buName){
        List<UserBatchProgressDTO> progress=batchProgressService.calculateBuProgress(buName);
        if(!progress.isEmpty()){
            return ResponseEntity.ok(progress);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
