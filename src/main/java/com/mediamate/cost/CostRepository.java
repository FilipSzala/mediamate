package com.mediamate.cost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CostRepository extends JpaRepository<Cost,Long> {
    @Query("SELECT c FROM Cost c WHERE c.realEstateId = :realEstateId AND FUNCTION('YEAR', c.createdDay) = :year AND FUNCTION('MONTH', c.createdDay) = :month")
    Optional<Cost> findCostByRealEstateIdAndCreationDate(
            @Param("realEstateId") Long realEstateId,
            @Param("year") int year,
            @Param("month") int month);
}
