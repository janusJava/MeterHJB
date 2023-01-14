package com.seafta.meterhj.boundary.measurement;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/measurement")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public interface MeasurementApi {

    @Operation(summary = "GetMeasurement", description = "GET_MEASUREMENT")
    @GetMapping(value = "/{measurementId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    MeasurementDetails getMeasurement(@PathVariable("measurementId")UUID measurementId);

    @Operation(summary = "GetMeasurements", description = "GET_MEASUREMENTS")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MeasurementDetails> getMeasurements();

    @Operation(summary = "GetMeasurementsByMeter", description = "GET_MEASUREMENTS_METER")
    @GetMapping(value = "/meter/{meterId}",produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MeasurementDetails> getMeasurementsByMeter(@PathVariable("meterId") UUID meterId);

    @Operation(summary = "DeleteMeasurement", description = "DELETE_MEASUREMENT")
    @DeleteMapping(value = "/{measurementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMeasurement(@PathVariable("measurementId") UUID measurementId);
}
