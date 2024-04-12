package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BatchWiseProgressDTO {
    private long batchId;
    private double batchProgress;

}

