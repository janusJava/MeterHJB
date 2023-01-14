package com.seafta.meterhj.domain.persistence.model.measurement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seafta.meterhj.domain.persistence.model.measurement.image.MeasurementImage;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "meter")
@Table
@Entity
public class Measurement {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "value")
    private float value;

    @Column
    private String address;

    @OneToOne(mappedBy = "measurement",
              orphanRemoval = true,
              fetch = FetchType.EAGER)
    private MeasurementImage image;

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    @JsonIgnore
    private Meter meter;

    @NotNull
    private OffsetDateTime created;

    public static Measurement buildMeasurement(Meter meter, float value, String address) {
        return Measurement.builder()
                .value(value)
                .meter(meter)
                .address(address)
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .build();
    }

    @PrePersist
    private void onPrePersist() {
        created = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
