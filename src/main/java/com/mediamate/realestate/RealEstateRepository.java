package com.mediamate.realestate;

import com.mediamate.realestate.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate,Long> {
    Set<RealEstate> findByOwnerId(Long id);
}
