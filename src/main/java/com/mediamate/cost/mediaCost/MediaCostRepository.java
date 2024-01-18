package com.mediamate.cost.mediaCost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaCostRepository extends JpaRepository<MediaCost,Long> {
}
