package com.seafta.meterhj.domain.dto.measurement;

import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDetails {

    private UUID measurementId;

    private float value;

    private MeasurementImage image;

    private OffsetDateTime created;

    private String address;
}
