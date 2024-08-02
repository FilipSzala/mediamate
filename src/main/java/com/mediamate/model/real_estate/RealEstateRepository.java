package com.mediamate.model.real_estate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    @Query("SELECT r FROM RealEstate r JOIN r.users u WHERE u.id = :userId")
    List<RealEstate> findByUserId(@Param("userId") Long id);
}