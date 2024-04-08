package com.thbs.cpt.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseIdsRequest {
    private long UserId;
    private List<Integer> courseIds;

}
