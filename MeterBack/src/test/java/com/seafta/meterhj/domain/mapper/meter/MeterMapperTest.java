package com.seafta.meterhj.domain.mapper.meter;

import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class MeterMapperTest {

    private final MeterMapper mapper = Mappers.getMapper(MeterMapper.class);

    @Test
    @DisplayName("should map meter to meterDetails")
    void shouldMapMeterToMeterDetails() {
        Meter meter = Meter.builder()
                .id(UUID.randomUUID())
                .type(MeterType.G1)
                .measurements(Collections.emptySet())
                .account(null)
                .build();

        MeterDetails details = mapper.toMeterDetails(meter);
        Assertions.assertEquals(details.getMeterId(), meter.getId());
    }

    @Test
    @DisplayName("should map meter to meterSnapshot")
    void shouldMapMeterToMeterSnapshot() {
        Meter meter = Meter.builder()
                .id(UUID.randomUUID())
                .type(MeterType.G1)
                .measurements(Collections.emptySet())
                .account(null)
                .build();

        MeterSnapshot snapshot = mapper.toMeterSnapshot(meter);
        Assertions.assertEquals(snapshot.getMeterId(), meter.getId());
    }

    @Test
    @DisplayName("should map meter to meterUpdatedSnapshot")
    void shouldMapMeterToMeterUpdatedSnapshot() {
        Meter meter = Meter.builder()
                .id(UUID.randomUUID())
                .type(MeterType.G1)
                .measurements(Collections.emptySet())
                .account(null)
                .build();

        MeterUpdatedSnapshot snapshot = mapper.toMeterUpdatedSnapshot(meter);
        Assertions.assertEquals(snapshot.getMeterId(), meter.getId());
    }
}
