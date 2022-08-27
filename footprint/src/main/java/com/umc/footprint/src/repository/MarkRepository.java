package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

    Optional<Mark> findByCourseIdxAndUserIdx(Integer courseIdx, Integer userIdx);
}
