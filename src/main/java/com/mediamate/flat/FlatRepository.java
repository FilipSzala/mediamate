package com.mediamate.flat;

import com.mediamate.flat.Flat;
import com.mediamate.realestate.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepository extends JpaRepository<Flat,Long> {
    List<Flat> findByRealEstateId(Long realEstateId);
}
