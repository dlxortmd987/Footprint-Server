package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
    Optional<Goal> findByUserIdx(@Param("userIdx") Integer userIdx);
}
