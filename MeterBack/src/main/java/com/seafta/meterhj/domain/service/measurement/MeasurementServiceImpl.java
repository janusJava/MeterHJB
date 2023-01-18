package com.seafta.meterhj.domain.service.measurement;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.mapper.measurement.MeasurementMapper;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import com.seafta.meterhj.domain.persistence.repository.measurement.MeasurementRepository;
import com.seafta.meterhj.domain.persistence.repository.meter.MeterRepository;
import com.seafta.meterhj.domain.service.measurement.image.MeasurementImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository repository;
    private final MeasurementMapper mapper;
    private final MeterRepository meterRepository;

    @Override
    public MeasurementDetails createMeasurement(@NotNull UUID meterId , float value, String address) {
        log.info("Service: Creating measurement {meter id: {}}", meterId);
        Meter meter = meterRepository.findById(meterId).get();
        MeasurementDetails result = mapper.toMeasurementDetails(repository.save(Measurement.buildMeasurement(meter, value, address)));
        log.debug("Service: created measurement {result: {}}", result);
        return result;
    }

    @Override
    public MeasurementDetails getMeasurement(@NotNull UUID measurementId) {
        log.info("Service: Getting measurement {id: {}}", measurementId);
        MeasurementDetails result = mapper.toMeasurementDetails(repository.findById(measurementId).get());
        log.debug("Service: Got measurement {result: {}}", result);
        return result;
    }

    @Override
    public List<MeasurementDetails> getMeasurements() {
        log.info("Service: Getting measurements");
        List<MeasurementDetails> result = repository.findAll().stream()
                .map(mapper::toMeasurementDetails).collect(Collectors.toList());
        log.debug("Service: Got measurements {result: {}}", result);
        return result;
    }

    @Override
    public List<MeasurementDetails> getMeasurementsByMeter(@NotNull UUID meterId) {
        log.info("Service: Getting measurements by meter id {id: {}}", meterId);
        List<MeasurementDetails> result = repository.getMeasurementsByMeterId(meterId).stream().map(mapper::toMeasurementDetails).collect(Collectors.toList());
        log.debug("Service: Got measurements by meter id {result: {}}", result);
        return result;
    }

    @Override
    public void deleteMeasurement(@NotNull UUID measurementId) {
        log.info("Deleting measurement by id {id: {}}", measurementId);
        repository.deleteById(measurementId);
        log.debug("Deleted measurement by id {id: {}}", measurementId);
    }
}
