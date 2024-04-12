package com.thbs.cpt.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class LearningResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long learningResourceId;

    private long batchId;
	private long courseId;
	private long topicId;

    @OneToMany(mappedBy = "learningResource")
    private List<Resource> resources;
}
