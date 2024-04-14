package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserBatchProgressDTO {
    private Long userId;
    private double overallProgress;

}
