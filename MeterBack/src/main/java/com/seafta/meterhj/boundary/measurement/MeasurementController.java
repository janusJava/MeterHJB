package com.seafta.meterhj.boundary.measurement;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.service.measurement.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MeasurementController implements MeasurementApi {

    private final MeasurementService service;

    @Override
    public MeasurementDetails getMeasurement(@NotNull UUID measurementId) {
        log.info("Measurement controller: Getting measurement {id: {}}", measurementId);
        MeasurementDetails result = service.getMeasurement(measurementId);
        log.debug("Measurement controller: Got measurement {result: {}}", result);
        return result;
    }

    @Override
    public List<MeasurementDetails> getMeasurements() {
        log.info("Measurement controller: Getting measurements");
        List<MeasurementDetails> result = service.getMeasurements();
        log.debug("Measurement controller: Got measurements {result: {}}", result);
        return result;
    }

    @Override
    public List<MeasurementDetails> getMeasurementsByMeter(@NotNull UUID meterId) {
        log.info("Measurement controller: Getting measurements by meter id {id: {}}", meterId);
        List<MeasurementDetails> result = service.getMeasurementsByMeter(meterId);
        log.debug("Measurement controller: Got measurements by meter id{result: {}}", result);
        return result;
    }

    @Override
    public void deleteMeasurement(@NotNull UUID measurementId) {
        log.info("Measurement controller: Deleting measurement {id: {}}", measurementId);
        service.deleteMeasurement(measurementId);
        log.debug("Measurement controller: Deleted measurement");
    }
}
