package com.seafta.meterhj.boundary.meter;

import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;
import com.seafta.meterhj.domain.service.meter.MeterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MeterController implements MeterApi {

    private final MeterService service;

    @Override
    public MeterSnapshot createMeter(@NotNull @Valid MeterCreateRequest request, @NotNull UUID accountId) {
        log.info("Meter controller: Creating meter {id: {}, request: {}}", accountId, request);
        MeterSnapshot result = service.createMeter(request, accountId);
        log.debug("Meter controller: Created meter {result: {}}", result);
        return result;
    }

    @Override
    public MeterDetails getMeter(@NotNull UUID meterId) {
        log.info("Meter controller: Getting meter {id: {}}", meterId);
        MeterDetails result = service.getMeter(meterId);
        log.debug("Meter Controller: Got meter {result: {}}", result);
        return result;
    }

    @Override
    public List<MeterDetails> getMeters() {
        log.info("Meter controller: Getting meters");
        List<MeterDetails> result = service.getMeters();
        log.debug("Meter Controller: Got meters {result: {}}", result);
        return result;
    }

    @Override
    public MeterUpdatedSnapshot updateMeter(@NotNull UUID meterId, @NotNull @Valid MeterUpdateRequest request) {
        log.info("Meter controller: Updating meter {id: {}, request: {}}", meterId, request);
        MeterUpdatedSnapshot result = service.updateMeter(meterId, request);
        log.debug("Meter controller: Updated meter {result: {}}", result);
        return result;
    }

    @Override
    public void deleteMeter(@NotNull UUID meterId) {
        log.info("Meter controller: Deleting meter {id: {}}", meterId);
        service.deleteMeter(meterId);
        log.debug("Meter controller: Deleted meter {id: {}}", meterId);
    }

    @Override
    public List<MeterDetails> getMetersByAccount(@NotNull UUID accountId) {
        log.info("Meter controller: Getting meters by account {id: {}}", accountId);
        List<MeterDetails> result = service.getMetersByAccount(accountId);
        log.debug("Meter Controller: Got meters by account {result: {}}", result);
        return result;
    }
}
