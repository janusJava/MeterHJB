package com.seafta.meterhj.domain.request.meter;

import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MeterUpdateRequest {

    private String name;
    private MeterType type;
}
