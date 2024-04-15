package com.thbs.cpt.DTO;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BatchWiseProgressDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Given
        long batchId = 1L;
        double batchProgress = 75.0;

        // When
        BatchWiseProgressDTO batchWiseProgressDTO = new BatchWiseProgressDTO(batchId, batchProgress);

        // Then
        assertThat(batchWiseProgressDTO).isNotNull();
        assertThat(batchWiseProgressDTO.getBatchId()).isEqualTo(batchId);
        assertThat(batchWiseProgressDTO.getBatchProgress()).isEqualTo(batchProgress);
    }

    @Test
    void testSetter() {
        // Given
        long batchId = 2L;
        double batchProgress = 80.0;
        BatchWiseProgressDTO batchWiseProgressDTO = new BatchWiseProgressDTO();

        // When
        batchWiseProgressDTO.setBatchId(batchId);
        batchWiseProgressDTO.setBatchProgress(batchProgress);

        // Then
        assertThat(batchWiseProgressDTO.getBatchId()).isEqualTo(batchId);
        assertThat(batchWiseProgressDTO.getBatchProgress()).isEqualTo(batchProgress);
    }
}
