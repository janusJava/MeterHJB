package com.seafta.meterhj.domain.persistence.repository.measurement;

import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> getMeasurementsByMeterId(UUID meterId);
}
