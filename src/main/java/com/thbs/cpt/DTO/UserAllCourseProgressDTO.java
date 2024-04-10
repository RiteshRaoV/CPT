package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAllCourseProgressDTO {
        private long userId;
        private int courseId;
        private double overallProgress;
}
