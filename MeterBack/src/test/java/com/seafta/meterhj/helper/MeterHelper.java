package com.seafta.meterhj.helper;

import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;

public class MeterHelper {

    public static MeterCreateRequest buildMeterCreateRequest() {
        return MeterCreateRequest.builder()
                .type(MeterType.G1)
                .build();
    }

    public static MeterUpdateRequest buildMeterUpdateRequest() {
        return MeterUpdateRequest.builder()
                .type(MeterType.G6)
                .build();
    }
}
