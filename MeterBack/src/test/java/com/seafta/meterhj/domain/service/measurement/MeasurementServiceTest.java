package com.seafta.meterhj.domain.service.measurement;

import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.persistence.repository.measurement.MeasurementRepository;
import com.seafta.meterhj.domain.persistence.repository.meter.MeterRepository;
import com.seafta.meterhj.domain.service.account.AccountService;
import com.seafta.meterhj.domain.service.meter.MeterService;
import com.seafta.meterhj.helper.AccountHelper;
import com.seafta.meterhj.helper.MeterHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MeasurementServiceTest {

    @Autowired
    private MeasurementService service;
    @Autowired
    private MeasurementRepository repository;
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MeterRepository meterRepository;
    @Autowired
    private MeterService meterService;
    private MeterSnapshot meter;
    private AccountSnapshot account;


    @BeforeAll
    public void before() {
        account = accountService.createAccount(AccountHelper.buildAccountCreateRequest());
        meter = meterService.createMeter(MeterHelper.buildMeterCreateRequest(), account.getAccountId());
    }

    @AfterAll
    public void after() {
        accountRepository.deleteAll();
    }

    @AfterEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("should create measurement")
    void createMeasurement() {
        MeasurementDetails result = service.createMeasurement(meter.getMeterId(), 30, "Bukowa 8, Busko Zdrój, 28-100");
        Assertions.assertEquals(result.getValue(), 30);
    }

    @Test
    @DisplayName("should get measurement")
    void shouldGetMeasurement() {
        MeasurementDetails details = service.createMeasurement(meter.getMeterId(), 30, "Bukowa 8, Busko Zdrój, 28-100");
        MeasurementDetails result = service.getMeasurement(details.getMeasurementId());
        Assertions.assertEquals(result.getValue(), details.getValue());
    }

    @Test
    @DisplayName("should get measurements")
    void shouldGetMeasurements() {
        MeasurementDetails details = service.createMeasurement(meter.getMeterId(), 30,"Bukowa 8, Busko Zdrój, 28-100");
        MeasurementDetails details1 = service.createMeasurement(meter.getMeterId(), 20,"Bukowa 8, Busko Zdrój, 28-100");
        List<MeasurementDetails> result = service.getMeasurements();
        Assertions.assertEquals(result.get(0).getValue(), details.getValue());
        Assertions.assertEquals(result.get(1).getValue(), details1.getValue());
    }

    @Test
    @DisplayName("should get measurements by meter")
    void shouldGetMeasurementsByMeter() {
        MeasurementDetails details = service.createMeasurement(meter.getMeterId(), 30,"Bukowa 8, Busko Zdrój, 28-100");
        MeasurementDetails details1 = service.createMeasurement(meter.getMeterId(), 20,"Bukowa 8, Busko Zdrój, 28-100");
        List<MeasurementDetails> result = service.getMeasurementsByMeter(meter.getMeterId());
        Assertions.assertEquals(result.get(0).getValue(), details.getValue());
        Assertions.assertEquals(result.get(1).getValue(), details1.getValue());
    }

    @Test
    @DisplayName("should delete measurement")
    void shouldDeleteMeasurement() {
        MeasurementDetails details = service.createMeasurement(meter.getMeterId(), 30,"Bukowa 8, Busko Zdrój, 28-100");
        service.deleteMeasurement(details.getMeasurementId());
        Assertions.assertEquals(repository.count(), 0);
        Assertions.assertFalse(repository.existsById(details.getMeasurementId()));
    }
}
