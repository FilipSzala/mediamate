package com.mediamate.repository;

import com.mediamate.model.Owner;
import com.mediamate.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate,Long> {
}
