package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

    Optional<Mark> findByCourseIdxAndUserIdx(Integer courseIdx, Integer userIdx);

    @Query(value = "SELECT courseIdx FROM Mark WHERE userIdx = (:userIdx) AND mark = (:mark)", nativeQuery = true)
    List<Integer> getCourseIdxByUserIdxAndMark(@Param("userIdx") Integer userIdx, @Param("mark") Boolean mark);
}
