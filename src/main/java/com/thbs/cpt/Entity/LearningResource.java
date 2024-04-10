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

    private int batchId;
	private int courseId;
	private int topicId;

    @OneToMany(mappedBy = "learningResource")
    private List<Resource> resources;
}
