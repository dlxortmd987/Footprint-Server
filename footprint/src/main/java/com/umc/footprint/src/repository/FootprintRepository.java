package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Footprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootprintRepository extends JpaRepository<Footprint, Integer> {

//    List<Footprint> findAllByWalk

}
