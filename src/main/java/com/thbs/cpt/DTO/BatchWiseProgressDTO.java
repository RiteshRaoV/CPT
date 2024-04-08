package com.thbs.cpt.DTO;

public class BatchWiseProgressDTO {
    private int batchId;
    private double batchProgress;

    public BatchWiseProgressDTO() {
    }

    public BatchWiseProgressDTO(int batchId, double batchProgress) {
        this.batchId = batchId;
        this.batchProgress = batchProgress;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public double getBatchProgress() {
        return batchProgress;
    }

    public void setBatchProgress(double batchProgress) {
        this.batchProgress = batchProgress;
    }
}

