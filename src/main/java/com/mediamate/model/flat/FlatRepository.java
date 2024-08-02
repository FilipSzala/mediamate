package com.mediamate.model.flat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepository extends JpaRepository<Flat,Long> {
    List<Flat> findByRealEstateId(Long realEstateId);

}
