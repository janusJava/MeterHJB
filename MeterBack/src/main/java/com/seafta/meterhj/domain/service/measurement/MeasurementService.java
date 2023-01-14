package com.seafta.meterhj.domain.service.measurement;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface MeasurementService {

    MeasurementDetails createMeasurement(@NotNull UUID meterId, float value, String address);
    MeasurementDetails getMeasurement(@NotNull UUID measurementId);
    List<MeasurementDetails> getMeasurements();
    List<MeasurementDetails> getMeasurementsByMeter(@NotNull UUID meterId);
    void deleteMeasurement(@NotNull UUID measurementId);
}
