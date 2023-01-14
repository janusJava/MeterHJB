package com.seafta.meterhj.domain.mapper.meter;

import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeterMapper {

    @Mapping(target = "meterId", source = "id")
    MeterSnapshot toMeterSnapshot(Meter meter);

    @Mapping(target = "meterId", source = "id")
    MeterDetails toMeterDetails(Meter meter);

    @Mapping(target = "meterId", source = "id")
    MeterUpdatedSnapshot toMeterUpdatedSnapshot(Meter meter);
}
