package com.seafta.meterhj.domain.mapper.meter;

import com.seafta.meterhj.domain.dto.measurement.MeasurementDetails;
import com.seafta.meterhj.domain.dto.meter.MeterDetails;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.measurement.Measurement;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-12T17:37:02+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class MeterMapperImpl implements MeterMapper {

    @Override
    public MeterSnapshot toMeterSnapshot(Meter meter) {
        if ( meter == null ) {
            return null;
        }

        MeterSnapshot meterSnapshot = new MeterSnapshot();

        meterSnapshot.setMeterId( meter.getId() );
        meterSnapshot.setType( meter.getType() );
        Set<Measurement> set = meter.getMeasurements();
        if ( set != null ) {
            meterSnapshot.setMeasurements( new HashSet<Measurement>( set ) );
        }
        meterSnapshot.setAccount( meter.getAccount() );

        return meterSnapshot;
    }

    @Override
    public MeterDetails toMeterDetails(Meter meter) {
        if ( meter == null ) {
            return null;
        }

        MeterDetails meterDetails = new MeterDetails();

        meterDetails.setMeterId( meter.getId() );
        meterDetails.setName( meter.getName() );
        meterDetails.setMeasurements( measurementSetToMeasurementDetailsSet( meter.getMeasurements() ) );
        meterDetails.setAccount( meter.getAccount() );
        meterDetails.setType( meter.getType() );

        return meterDetails;
    }

    @Override
    public MeterUpdatedSnapshot toMeterUpdatedSnapshot(Meter meter) {
        if ( meter == null ) {
            return null;
        }

        MeterUpdatedSnapshot meterUpdatedSnapshot = new MeterUpdatedSnapshot();

        meterUpdatedSnapshot.setMeterId( meter.getId() );
        meterUpdatedSnapshot.setType( meter.getType() );
        meterUpdatedSnapshot.setAccount( meter.getAccount() );

        return meterUpdatedSnapshot;
    }

    protected MeasurementDetails measurementToMeasurementDetails(Measurement measurement) {
        if ( measurement == null ) {
            return null;
        }

        MeasurementDetails measurementDetails = new MeasurementDetails();

        measurementDetails.setValue( measurement.getValue() );
        measurementDetails.setImage( measurement.getImage() );
        measurementDetails.setCreated( measurement.getCreated() );
        measurementDetails.setAddress( measurement.getAddress() );

        return measurementDetails;
    }

    protected Set<MeasurementDetails> measurementSetToMeasurementDetailsSet(Set<Measurement> set) {
        if ( set == null ) {
            return null;
        }

        Set<MeasurementDetails> set1 = new HashSet<MeasurementDetails>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Measurement measurement : set ) {
            set1.add( measurementToMeasurementDetails( measurement ) );
        }

        return set1;
    }
}
