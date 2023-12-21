package com.mediamate.realestate;

import com.mediamate.realestate.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate,Long> {
    List<RealEstate> findByOwnerId(Long id);
}
