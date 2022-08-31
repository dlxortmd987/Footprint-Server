package com.umc.footprint.src.goal.model.repository;

import com.umc.footprint.src.goal.model.entity.GoalDayNext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalDayNextRepository extends JpaRepository<GoalDayNext, Integer> {

    Optional<GoalDayNext> findByUserIdx(int userIdx);

    List<GoalDayNext> findAll();
}
