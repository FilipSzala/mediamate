package com.mediamate.meter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Meter,Long> {


    @Query("SELECT m FROM Meter m WHERE m.flat.id = :flatId AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    List<Meter> findMetersByFlatIdAndYearMonth(
            @Param("flatId") Long flatId,
            @Param("year") int year,
            @Param("month") int month);
}