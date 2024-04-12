package com.thbs.cpt.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgressDTO {
    
    private Long userId;
    private List<CourseDTO> courses;
}
