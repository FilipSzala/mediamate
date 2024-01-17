package com.mediamate.image;

import com.mediamate.YearMonthResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query("SELECT i FROM Image i WHERE i.realEstateId = :realEstateId AND i.imageType = :imageType AND i.createDay = :createDay")
    List<Image> findImagesByRealEstateIdAndImageTypeForCurrentDay(
            @Param("realEstateId") Long realEstateId,
            @Param("imageType") ImageType imageType,
            @Param("createDay") LocalDate createDay);

    @Query("SELECT new com.mediamate.YearMonthResult(YEAR(i.createDay), MONTH(i.createDay)) " +
            "FROM Image i " +
            "WHERE i.realEstateId = :realEstateId " +
            "GROUP BY YEAR(i.createDay), MONTH(i.createDay)")
    List<YearMonthResult> findAllDistinctYearMonthByRealEstateId(@Param("realEstateId") Long realEstateId);

    @Query("SELECT i FROM Image i WHERE i.realEstateId = :realEstateId AND i.imageType = :imageType AND YEAR(i.createDay) = :year AND MONTH(i.createDay) = :month")
    List<Image> findImagesByRealEstateIdAndImageTypeAndYearMonth(
            @Param("realEstateId") Long realEstateId,
            @Param("imageType") ImageType imageType,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT i FROM Image i WHERE i.realEstateId = :realEstateId AND i.imageType IS NULL")
    List<Image> findImagesWithoutTypeByRealEstateId(@Param("realEstateId") Long realEstateId);
}
