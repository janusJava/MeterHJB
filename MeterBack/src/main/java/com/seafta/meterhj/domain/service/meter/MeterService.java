package com.seafta.meterhj.domain.service.meter;

import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface MeterService {

    MeterSnapshot createMeter(@NotNull @Valid MeterCreateRequest request, @NotNull UUID accountId);
    MeterDetails getMeter(@NotNull UUID meterId);
    MeterUpdatedSnapshot updateMeter(@NotNull UUID meterId, @NotNull @Valid MeterUpdateRequest request);
    List<MeterDetails> getMeters();
    List<MeterDetails> getMetersByAccount(@NotNull UUID accountId);
    void deleteMeter(@NotNull UUID meterId);
}
