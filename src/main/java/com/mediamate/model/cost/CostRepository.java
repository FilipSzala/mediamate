package com.mediamate.model.cost;

import com.mediamate.model.cost.additionalCost.AdditionalCost;
import com.mediamate.model.cost.media_cost.MediaCost;
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

    @Query("SELECT c FROM Cost c WHERE c.realEstate.id = :realEstateId AND TYPE(c) = AdditionalCost AND c.createdAt >= :startDate AND c.createdAt <= :endDate")
    List<Cost> findAdditionalCostsInLastMonth(
            @Param("realEstateId") Long realEstateId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Cost c WHERE TYPE(c) = 'MediaCost' AND c.realEstate.id = :realEstateId ORDER BY c.createdAt DESC")
    List<Cost> findMediaCostByDateDesc(@Param("realEstateId") Long realEstateId);

    @Query("SELECT c FROM Cost c WHERE TYPE(c) = 'AdditionalCost' AND c.realEstate.id = :realEstateId AND c.createdAt = (SELECT MAX(c2.createdAt) FROM Cost c2 WHERE TYPE(c2) = 'AdditionalCost' AND c2.realEstate.id = :realEstateId)")
    List<Cost> findLatestAdditionalCostByRealEstateId(@Param("realEstateId") Long realEstateId);

    @Query("SELECT c FROM Cost c WHERE c.realEstate.id = :realEstateId AND TYPE(c) = AdditionalCost AND c.createdAt >= :startDate AND c.createdAt < :endDate")
    List<AdditionalCost> findAdditionalCostByRealEstateIdAndCostTypeAndDate(
            @Param("realEstateId") Long realEstateId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);



    @Query("SELECT c FROM Cost c WHERE c.realEstate.id = :realEstateId AND TYPE(c) = MediaCost AND c.createdAt >= :startDate AND c.createdAt <= :endDate")
    List<MediaCost> findMediaCostByRealEstateIdAndDate(
            @Param("realEstateId") Long realEstateId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}