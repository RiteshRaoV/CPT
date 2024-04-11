package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
// @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTopicProgressDTO {
    private long userId;
    private double topicProgress;
    
}