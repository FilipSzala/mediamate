package com.mediamate.model;

import com.mediamate.model.additionalCost.AdditionalCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CostRepository extends JpaRepository<Cost,Long> {
    @Query("SELECT c FROM Cost c WHERE c.realEstate.id = :realEstateId AND TYPE(c) = MediaCost AND c.createdAt >= :startDate AND c.createdAt < :endDate")
    Optional<Cost> findMediaCostByRealEstateIdAndCostTypeInCurrentMonth(
            @Param("realEstateId") Long realEstateId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Cost c WHERE c.realEstate.id = :realEstateId AND TYPE(c) = AdditionalCost AND c.createdAt >= :startDate AND c.createdAt < :endDate")
    List<AdditionalCost> findAdditionalCostByRealEstateIdAndCostTypeInCurrentMonth(
            @Param("realEstateId") Long realEstateId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}