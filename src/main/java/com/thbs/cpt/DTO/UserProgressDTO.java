package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgressDTO {
    private long userId;
    private double overallProgress;
}
