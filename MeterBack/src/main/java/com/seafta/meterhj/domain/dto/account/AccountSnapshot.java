package com.seafta.meterhj.domain.dto.account;

import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSnapshot {

  @NotNull
  private UUID accountId;

  @NotBlank
  private String email;

  private String passwordHash;

  private String fullName;

  private Set<Meter> meters;
}
