package com.seafta.meterhj.helper;

import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class WebHelper {

    public static HttpEntity<?> assignHeaders(Object body) {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(body, headers);
    }
}
