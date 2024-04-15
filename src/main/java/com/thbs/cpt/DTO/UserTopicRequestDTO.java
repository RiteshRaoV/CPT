package com.thbs.cpt.DTO;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserTopicRequestDTO {
    private Long userId;
    private List<Long> topicIds;

    
}
