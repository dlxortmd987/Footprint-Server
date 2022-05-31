package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.GoalDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalDayRepository extends JpaRepository<GoalDay, Integer> {

    List<GoalDay> findByUserIdx(int userIdx);
}
