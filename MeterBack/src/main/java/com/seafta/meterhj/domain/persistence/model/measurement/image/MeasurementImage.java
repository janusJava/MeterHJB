package com.seafta.meterhj.domain.persistence.model.measurement.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "measurement")
@Table(name = "measurement_image")
@Entity
public class MeasurementImage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column
    private String name;

    @Lob
    @JsonIgnore
    private byte[] data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "measurement_id", referencedColumnName = "id")
    @JsonIgnore
    private Measurement measurement;

    @NotNull
    private OffsetDateTime created;

    public static MeasurementImage buildMeasurementImage(MultipartFile file, Measurement measurement) throws IOException {
        return MeasurementImage.builder()
                .name(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .data(file.getBytes())
                .measurement(measurement)
                .build();
    }

    @PrePersist
    private void onPrePersist() {
        created = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
