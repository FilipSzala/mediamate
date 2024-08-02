package com.mediamate.model.media_summary;

import com.mediamate.model.flat.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaSummaryRepository extends JpaRepository<MediaSummary,Long> {
    @Query("SELECT ms FROM MediaSummary ms " +
            "WHERE ms.flat IN :flats AND ms.createdAt = " +
            "(SELECT MAX(ms2.createdAt) FROM MediaSummary ms2 WHERE ms2.flat = ms.flat)")
    List<MediaSummary> findLastMediaSummariesByFlats(@Param("flats") List<Flat> flats);
}
