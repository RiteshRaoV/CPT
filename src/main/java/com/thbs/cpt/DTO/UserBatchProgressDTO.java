package com.thbs.cpt.DTO;

public class UserBatchProgressDTO {
    private Long userId;
    private double overallProgress;

    public UserBatchProgressDTO(Long userId, double overallProgress) {
        this.userId = userId;
        this.overallProgress = overallProgress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getOverallProgress() {
        return overallProgress;
    }

    public void setOverallProgress(double overallProgress) {
        this.overallProgress = overallProgress;
    }
}
