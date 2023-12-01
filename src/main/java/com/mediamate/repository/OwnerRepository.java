package com.mediamate.repository;

import com.mediamate.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface OwnerRepository extends JpaRepository<Owner,Long> {

    @Query("SELECT u FROM Owner u WHERE u.name = :name")
    Optional<Owner> findByName(@Param("name") String name);
}
