package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBatchProgressDTO {
    private Long userId;
    private double overallProgress;

}
