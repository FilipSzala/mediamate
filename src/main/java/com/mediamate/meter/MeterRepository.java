package com.mediamate.meter;

import com.mediamate.flat.Flat;
import com.mediamate.realestate.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<Meter,Long> {




    @Query ("Select m From Meter m WHERE m.meterType = :meterType AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    Optional<Meter> findMeterByYearMonthDateAndMeterType(
            @Param("meterType")MeterType meterType,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT m FROM Meter m WHERE m.flat = :flat AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    List <Meter> findMeterByFlatAndYearMonth(
            @Param("flat") Flat flat,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT m FROM Meter m WHERE m.flat = :flat AND m.meterType = :meterType AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    Optional <Meter> findMeterByFlatAndTypeAndYearMonth(
            @Param("flat") Flat flat,
            @Param("meterType") MeterType meterType,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT m FROM Meter m WHERE m.realEstate = :realEstate AND m.meterType = :meterType AND FUNCTION('YEAR', m.createdAt) = :year AND FUNCTION('MONTH', m.createdAt) = :month")
    Optional <Meter> findMeterByRealEstateAndTypeAndYearMonth(
            @Param("realEstate") RealEstate realEstate,
            @Param("meterType") MeterType meterType,
            @Param("year") int year,
            @Param("month") int month);
}