package com.seafta.meterhj.domain.service.meter;

import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.repository.account.AccountRepository;
import com.seafta.meterhj.domain.persistence.repository.meter.MeterRepository;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;
import com.seafta.meterhj.domain.service.account.AccountService;
import com.seafta.meterhj.helper.AccountHelper;
import com.seafta.meterhj.helper.MeterHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
public class MeterServiceTest {

    @Autowired
    private MeterService meterService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private AccountRepository accountRepository;
    private AccountSnapshot account;

    @BeforeAll
    public void before() {
        account = accountService.createAccount(AccountHelper.buildAccountCreateRequest());
    }

    @AfterAll
    public void after() {
        accountRepository.deleteAll();
    }

    @AfterEach
    void setup() {
        meterRepository.deleteAll();
    }

    @Test
    @DisplayName("should create meter")
    void shouldCreateMeter() {
        MeterCreateRequest request = MeterHelper.buildMeterCreateRequest();
        MeterSnapshot details = meterService.createMeter(request, account.getAccountId());
        Assertions.assertEquals(details.getAccount().getId(), account.getAccountId());
    }

    @Test
    @DisplayName("should get meter")
    void shouldGetMeter() {
        MeterCreateRequest request = MeterHelper.buildMeterCreateRequest();
        MeterSnapshot details = meterService.createMeter(request, account.getAccountId());
        MeterDetails result = meterService.getMeter(details.getMeterId());
        Assertions.assertEquals(result.getAccount().getId(), account.getAccountId());
    }

    @Test
    @DisplayName("should get meters")
    void shouldGetMeters() {
        MeterCreateRequest request = MeterHelper.buildMeterCreateRequest();
        MeterSnapshot details = meterService.createMeter(request, account.getAccountId());
        MeterSnapshot details1 = meterService.createMeter(request, account.getAccountId());
        List<MeterDetails> result = meterService.getMeters();
        Assertions.assertEquals(result.get(0).getAccount().getId(), account.getAccountId());
        Assertions.assertEquals(result.get(0).getAccount().getId(), account.getAccountId());
        Assertions.assertEquals(meterRepository.count(), 2);
    }

    @Test
    @DisplayName("should update meter")
    void shouldUpdateMeter() {
        MeterSnapshot details = meterService.createMeter(MeterHelper.buildMeterCreateRequest(), account.getAccountId());
        MeterUpdateRequest request = MeterHelper.buildMeterUpdateRequest();
        MeterUpdatedSnapshot result = meterService.updateMeter(details.getMeterId(), MeterHelper.buildMeterUpdateRequest());
        Assertions.assertEquals(result.getType(), request.getType());
        Assertions.assertEquals(result.getAccount().getId(), account.getAccountId());
    }
    @Test
    @DisplayName("should get meters by account")
    void shouldGetMetersByAccount() {
        MeterCreateRequest request = MeterHelper.buildMeterCreateRequest();
        MeterSnapshot details = meterService.createMeter(request, account.getAccountId());
        MeterSnapshot details1 = meterService.createMeter(request, account.getAccountId());
        List<MeterDetails> result = meterService.getMetersByAccount(account.getAccountId());
        Assertions.assertEquals(result.get(0).getAccount().getId(), account.getAccountId());
        Assertions.assertEquals(result.get(0).getAccount().getId(), account.getAccountId());
        Assertions.assertEquals(meterRepository.count(), 2);
    }

    @Test
    @DisplayName("should delete meter")
    void shouldDeleteMeter() {
        MeterCreateRequest request = MeterHelper.buildMeterCreateRequest();
        MeterSnapshot details = meterService.createMeter(request, account.getAccountId());
        meterService.deleteMeter(details.getMeterId());
        Assertions.assertEquals(meterRepository.count(), 0);
        Assertions.assertFalse(meterRepository.existsById(details.getMeterId()));
    }
}
