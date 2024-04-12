package com.thbs.cpt.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResourceProgressDTO {
    private long userId;
    private double resourceProgress;
    
}