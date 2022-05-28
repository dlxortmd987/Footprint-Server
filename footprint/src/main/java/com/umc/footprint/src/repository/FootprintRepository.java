package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Footprint;
import com.umc.footprint.src.model.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FootprintRepository extends JpaRepository<Footprint, Integer> {
    List<String> getCoordinateByWalk(Walk walk);
}
