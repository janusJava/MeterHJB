package com.seafta.meterhj.boundary.meter;

import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/meter")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public interface MeterApi {

    @Operation(summary = "CreateMeter", description = "METER_CREATE_POST")
    @PostMapping(value = "/{accountId}",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    MeterSnapshot createMeter(@RequestBody @Valid MeterCreateRequest request, @PathVariable("accountId") @NotNull UUID accountId);

    @Operation(summary = "GetMeter", description = "METER_GET")
    @GetMapping(value = "/{meterId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    MeterDetails getMeter(@PathVariable("meterId") UUID meterId);

    @Operation(summary = "GetMeters", description = "METERS_GET")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    List<MeterDetails> getMeters();

    @Operation(summary = "UpdateMeter", description = "METER_UPDATE_PUT")
    @PutMapping(value = "/{meterId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    MeterUpdatedSnapshot updateMeter(@PathVariable("meterId") UUID meterId, @RequestBody @Valid MeterUpdateRequest request);

    @Operation(summary = "DeleteAccount", description = "METER_DELETE")
    @DeleteMapping(value = "/{meterId}")
    @ResponseStatus(NO_CONTENT)
    void deleteMeter(@PathVariable("meterId") UUID meterId);

    @Operation(summary = "GetMetersByAccount", description = "METERS_ACCOUNT_GET")
    @GetMapping(value = "/account/{accountId}",produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    List<MeterDetails> getMetersByAccount(@PathVariable("accountId") UUID accountId);
}
