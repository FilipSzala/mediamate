package com.mediamate.repository;

import com.mediamate.model.MeterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterValueRepository extends JpaRepository<MeterValue,Long> {
}
