package com.seafta.meterhj.domain.mapper.measurement;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.dto.measurement.image.MeasurementImageDetails;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeasurementMapper {

    @Mapping(target = "measurementId", source = "id")
    MeasurementDetails toMeasurementDetails(Measurement measurement);
}
