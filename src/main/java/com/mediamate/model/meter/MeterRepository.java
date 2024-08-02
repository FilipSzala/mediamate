package com.mediamate.model.meter;

import com.mediamate.model.real_estate.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {

    @Query("SELECT m FROM Meter m WHERE m.flat.id = :flatId AND m.meterType = :meterType AND m.meterOwnership = :meterOwnership AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    Optional<Meter> findMeterByFlatIdAndMeterTypeAndYearMonth(
            @Param("flatId") Long flatId,
            @Param("meterType") MeterType meterType,
            @Param("meterOwnership") MeterOwnership meterOwnership,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT m FROM Meter m WHERE m.realEstate = :realEstate AND m.meterType = :meterType AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    Optional<Meter> findMeterByRealEstateAndTypeAndYearMonth(
            @Param("realEstate") RealEstate realEstate,
            @Param("meterType") MeterType meterType,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT m FROM Meter m WHERE m.realEstate.id = :realEstateId AND m.meterType = :meterType AND m.meterOwnership = :meterOwnership AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    Optional<Meter> findMeterByRealEstateIdAndMeterTypeAndYearMonth(
            @Param("realEstateId") Long realEstateId,
            @Param("meterType") MeterType meterType,
            @Param("meterOwnership") MeterOwnership meterOwnership,
            @Param("year") int year,
            @Param("month") int month);
}
