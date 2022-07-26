package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Footprint;
import com.umc.footprint.src.model.Walk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FootprintRepository extends JpaRepository<Footprint, Integer> {
    List<String> findCoordinateByWalk(Walk walk);

    List<Footprint> findAllByWalkWalkIdx(int walkIdx);

    Optional<Footprint> findByFootprintIdx(int footprintIdx);

    long countByWalkAndStatus(Walk walk, String status);

    List<Footprint> findAllByWalkAndStatus(Walk walk, String Status);

    Page<Footprint> findByWalkAndStatusOrderByRecordAtAsc(Walk walk, String status, Pageable pageable);
}
