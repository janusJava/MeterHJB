package com.seafta.meterhj.domain.dto.meter;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterDetails {

    private UUID meterId;

    private String name;

    private Set<MeasurementDetails> measurements;

    private Account account;

    private MeterType type;
}
