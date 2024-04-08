package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAllCourseProgressDTO {
        private long userId;
        private int courseId;
        private double overallProgress;
}
