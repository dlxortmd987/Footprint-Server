package com.umc.footprint.src.goal.model.repository;

import com.umc.footprint.src.goal.model.entity.GoalNext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalNextRepository extends JpaRepository<GoalNext, Integer> {

    Optional<GoalNext> findByUserIdx(int userIdx);

    List<GoalNext> findAll();

}
