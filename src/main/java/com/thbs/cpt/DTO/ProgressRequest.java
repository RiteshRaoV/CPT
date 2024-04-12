package com.thbs.cpt.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProgressRequest {
    private long userId;
    private List<Long> courseIds;
}
