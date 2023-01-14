package com.seafta.meterhj.domain.dto.meter;

import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterUpdatedSnapshot {

    private UUID meterId;


    private MeterType type;

    private Account account;
}
