package com.mediamate.metervalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterValueRepository extends JpaRepository<MeterValue,Long> {
}