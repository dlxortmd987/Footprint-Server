package com.umc.footprint.src.footprints.repository;

import com.umc.footprint.src.footprints.model.entity.Footprint;
import com.umc.footprint.src.walks.model.entity.Walk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FootprintRepository extends JpaRepository<Footprint, Integer> {
    List<String> findCoordinateByWalk(Walk walk);

    List<Footprint> findAllByWalkWalkIdx(int walkIdx);

    Optional<Footprint> findByFootprintIdx(int footprintIdx);

    long countByWalkAndStatus(Walk walk, String status);

    List<Footprint> findAllByWalkAndStatus(Walk walk, String Status);

    Page<Footprint> findByWalkAndStatusOrderByCreateAtAsc(Walk walk, String status, Pageable pageable);
}
