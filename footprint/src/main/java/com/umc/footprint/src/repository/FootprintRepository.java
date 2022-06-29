package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Footprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FootprintRepository extends JpaRepository<Footprint, Integer> {

    List<Footprint> findAllByWalkWalkIdx(int walkIdx);

    Optional<Footprint> findByFootprintIdx(int footprintIdx);

    List<Footprint> findAllBy
}
