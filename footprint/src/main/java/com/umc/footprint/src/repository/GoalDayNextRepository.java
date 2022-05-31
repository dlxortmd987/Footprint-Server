package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.GoalDayNext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalDayNextRepository extends JpaRepository<GoalDayNext, Integer> {

    Optional<GoalDayNext> findByUserIdx(int userIdx);
}
