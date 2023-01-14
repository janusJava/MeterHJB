package com.seafta.meterhj.domain.service.meter;

import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.mapper.measurement.MeasurementMapper;
import com.seafta.meterhj.domain.mapper.meter.MeterMapper;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.persistence.repository.meter.MeterRepository;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.security.logging.SecurityMarkers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeterServiceImpl implements MeterService {

    private final MeterRepository meterRepository;
    private final MeterMapper mapper;

    private final MeasurementMapper measurementMapper;
    private final AccountRepository accountRepository;

    @Override
    public MeterSnapshot createMeter(@NotNull @Valid MeterCreateRequest request, @NotNull UUID accountId) {
        log.info("Service: Creating meter {request: {}}", request);
        Account account = accountRepository.findById(accountId).get();
        MeterSnapshot result = mapper.toMeterSnapshot(meterRepository.save(Meter.buildMeter(request, account)));
        log.debug("Service: Created meter {result: {}}", result);
        return result;
    }

    @Override
    public MeterDetails getMeter(@NotNull  UUID meterId) {
        log.info("Service: Getting meter {id: {}}", meterId);
        MeterDetails result = getMeterDetails(meterRepository.findById(meterId).get());
        log.debug("Service: Got meter {result: {}}", result);
        return result;
    }

    @Override
    public MeterUpdatedSnapshot updateMeter(@NotNull UUID meterId,@NotNull @Valid MeterUpdateRequest request) {
        log.info("Service: Updating meter {id: {}, request: {}}", meterId, request);
        Meter meter = meterRepository.findById(meterId).get();
        MeterUpdatedSnapshot result = mapper.toMeterUpdatedSnapshot(meter.editMeter(request));
        meterRepository.save(meter);
        log.debug("Service: Updated meter {result: {}}", result);
        return result;
    }

    @Override
    public List<MeterDetails> getMeters() {
        log.info("Service: Getting meters");
        List<MeterDetails> result = meterRepository.findAll().stream().map(this::getMeterDetails).collect(Collectors.toList());
        log.debug("Service: Got meters {result: {}}", result);
        return result;
    }

    @Override
    public List<MeterDetails> getMetersByAccount(@NotNull  UUID accountId) {
        log.info("Service: Getting meters by account id {id: {}}", accountId);
        List<MeterDetails> result  = meterRepository.getMetersByAccountId(accountId).stream().map(this::getMeterDetails).collect(Collectors.toList());
        log.debug("Service: Getting meters by account id {result: {}}", result);
        return result;
    }

    @Override
    public void deleteMeter(@NotNull UUID meterId) {
        log.info("Service: Deleting meter");
        meterRepository.deleteById(meterId);
        log.debug("Service: Deleted meter");
    }

    private MeterDetails getMeterDetails(Meter meter) {
        return MeterDetails.builder()
                .meterId(meter.getId())
                .name(meter.getName())
                .measurements(meter.getMeasurements().stream().map(measurementMapper::toMeasurementDetails).collect(Collectors.toSet()))
                .account(meter.getAccount())
                .type(meter.getType())
                .build();
    }
}
