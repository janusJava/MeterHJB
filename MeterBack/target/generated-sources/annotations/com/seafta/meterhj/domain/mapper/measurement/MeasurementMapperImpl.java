package com.seafta.meterhj.domain.mapper.measurement;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-12T17:37:02+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class MeasurementMapperImpl implements MeasurementMapper {

    @Override
    public MeasurementDetails toMeasurementDetails(Measurement measurement) {
        if ( measurement == null ) {
            return null;
        }

        MeasurementDetails measurementDetails = new MeasurementDetails();

        measurementDetails.setMeasurementId( measurement.getId() );
        measurementDetails.setValue( measurement.getValue() );
        measurementDetails.setImage( measurement.getImage() );
        measurementDetails.setCreated( measurement.getCreated() );
        measurementDetails.setAddress( measurement.getAddress() );

        return measurementDetails;
    }
}
