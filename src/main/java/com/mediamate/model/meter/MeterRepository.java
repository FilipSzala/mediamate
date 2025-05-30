package com.mediamate.model.meter;

import com.mediamate.model.real_estate.RealEstate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("SELECT m FROM Meter m " +
            "WHERE m.flat.id = :flatId " +
            "AND m.meterType = :meterType " +
            "AND m.meterOwnership = :meterOwnership " +
            "AND (YEAR(m.createdAt) < YEAR(CURRENT_DATE) " +
            "OR MONTH(m.createdAt) < MONTH(CURRENT_DATE)) " +
            "ORDER BY m.createdAt DESC")
    List<Meter> findLatestMeterByFlatIdAndMeterTypeExcludingCurrentMonth(
            @Param("flatId") Long flatId,
            @Param("meterType") MeterType meterType,
            @Param("meterOwnership") MeterOwnership meterOwnership);


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
    @Query("SELECT m FROM Meter m " +
            "WHERE m.meterType IN (0, 1) " +
            "AND m.flat.id IN :flatIds " +
            "AND m.createdAt = (SELECT MAX(m2.createdAt) " +
            "FROM Meter m2 " +
            "WHERE m2.meterType IN (0, 1) " +
            "AND m2.flat.id IN :flatIds)")
    List<Meter> findLastMetersByFlatIdsForColdAndWarmWater(
            @Param("flatIds") List<Long> flatIds);

    @Query("SELECT m FROM Meter m " +
            "WHERE m.flat.id IN :flatIds " +
            "AND m.createdAt = (SELECT MAX(m2.createdAt) " +
            "FROM Meter m2 " +
            "WHERE m2.flat.id IN :flatIds)")
    List<Meter> findLastMetersByFlatIds(
            @Param("flatIds") List<Long> flatIds);


    @Query("SELECT m FROM Meter m " +
            "WHERE ((:isFlat = true AND m.flat.id = :id) " +
            "   OR (:isFlat = false AND m.realEstate.id = :id)) " +
            "AND m.meterType = :meterType " +
            "AND m.meterOwnership = :meterOwnership " +
            "ORDER BY m.createdAt DESC")
    List<Meter> findLast12MetersByFlatOrRealEstateIdMeterTypeAndOwnership(
            @Param("id") Long id,
            @Param("meterType") MeterType meterType,
            @Param("meterOwnership") MeterOwnership meterOwnership,
            @Param("isFlat") boolean isFlat,
            Pageable pageable);

    @Query("SELECT m FROM Meter m " +
            "WHERE ((:isFlat = true AND m.flat.id = :id) " +
            "   OR (:isFlat = false AND m.realEstate.id = :id)) " +
            "AND m.meterType = :meterType " +
            "AND m.meterOwnership = :meterOwnership " +
            "AND FUNCTION('MONTH', m.createdAt) BETWEEN 5 AND 9 " +
            "ORDER BY m.createdAt DESC")
    List<Meter> findLast12MetersByFlatOrRealEstateIdMeterTypeAndMeterOwnershipInSummertime(
            @Param("id") Long id,
            @Param("meterType") MeterType meterType,
            @Param("meterOwnership") MeterOwnership meterOwnership,
            @Param("isFlat") boolean isFlat,
            Pageable pageable);

    @Query("SELECT m FROM Meter m " +
            "WHERE ((:isFlat = true AND m.flat.id = :id) " +
            "   OR (:isFlat = false AND m.realEstate.id = :id)) " +
            "AND m.meterType = :meterType " +
            "AND m.meterOwnership = :meterOwnership " +
            "AND FUNCTION('MONTH', m.createdAt) NOT BETWEEN 5 AND 9 " +
            "ORDER BY m.createdAt DESC")
    List<Meter> findLast12MetersByFlatOrRealEstateIdMeterTypeAndMeterOwnershipInWinterTime(
            @Param("id") Long id,
            @Param("meterType") MeterType meterType,
            @Param("meterOwnership") MeterOwnership meterOwnership,
            @Param("isFlat") boolean isFlat,
            Pageable pageable);

    @Query("SELECT m FROM Meter m " +
            "WHERE m.realEstate.id = :realEstateId " +
            "AND m.createdAt = (SELECT MAX(m2.createdAt) " +
            "FROM Meter m2 WHERE m2.realEstate.id = :realEstateId)")
    List<Meter> findLastMetersByRealestateIdWithMeterOwnershipRealEstate(@Param("realEstateId") Long realEstateId);
}
