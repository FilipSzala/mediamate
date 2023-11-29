package com.mediamate.repository;

import com.mediamate.model.Water;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterRepository extends JpaRepository<Water,Long> {
}
