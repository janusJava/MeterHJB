package com.seafta.meterhj.domain.persistence.model.meter;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "account")
@Table
@Entity
public class Meter {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private MeterType type;

    @OneToMany(mappedBy = "meter",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Measurement> measurements;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;

    public static Meter buildMeter(MeterCreateRequest request, Account account){
        return Meter.builder()
                .measurements(Collections.emptySet())
                .name(request.getName())
                .type(request.getType())
                .account(account)
                .build();
    }

    public Meter editMeter(@NotNull @Valid MeterUpdateRequest request) {
        this.name = request.getName();
        this.type = request.getType();
        return this;
    }
}
