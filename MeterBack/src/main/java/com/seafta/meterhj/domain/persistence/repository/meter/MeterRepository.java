package com.seafta.meterhj.domain.persistence.repository.meter;

import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {

    List<Meter> getMetersByAccountId(UUID accountId);
}
