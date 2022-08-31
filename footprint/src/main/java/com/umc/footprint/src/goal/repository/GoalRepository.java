package com.umc.footprint.src.goal.repository;

import com.umc.footprint.src.goal.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findByUserIdx(@Param("userIdx") Integer userIdx);

    Optional<List<Goal>> getByUserIdx(@Param("userIdx") Integer userIdx);
}
