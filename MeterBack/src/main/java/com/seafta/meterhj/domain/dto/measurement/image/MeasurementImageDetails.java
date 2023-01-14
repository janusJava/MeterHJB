package com.seafta.meterhj.domain.dto.measurement.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementImageDetails {

    private UUID imageId;

    private String imageName;

    private String downloadUri;
}
