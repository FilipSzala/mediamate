package com.mediamate.model.realestate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate,Long> {
    List<RealEstate> findByOwnerId(Long id);
}
