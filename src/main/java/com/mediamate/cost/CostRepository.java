package com.mediamate.cost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CostRepository extends JpaRepository<Cost,Long> {
/*    @Query("SELECT c FROM Cost c WHERE c.realEstate.id = :realEstate.id AND FUNCTION('YEAR', c.createdAt) = :year AND FUNCTION('MONTH', c.createdAt) = :month")
    List<Cost> findCostsByRealEstateIdAndCreationDate(
            @Param("realEstateId") Long realEstateId,
            @Param("year") int year,
            @Param("month") int month);*/
}
