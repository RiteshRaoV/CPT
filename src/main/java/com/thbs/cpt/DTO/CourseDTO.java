package com.thbs.cpt.DTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDTO {
    private long courseId;
    private double courseProgress;
    private List<TopicDTO> topics;
}
