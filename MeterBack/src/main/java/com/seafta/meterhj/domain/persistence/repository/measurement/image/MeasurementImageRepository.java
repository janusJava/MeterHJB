package com.seafta.meterhj.domain.persistence.repository.measurement.image;

import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementImageRepository extends JpaRepository<MeasurementImage, UUID> {
    List<MeasurementImage> getMeasurementImagesByMeasurementId(UUID measurementId);
}
