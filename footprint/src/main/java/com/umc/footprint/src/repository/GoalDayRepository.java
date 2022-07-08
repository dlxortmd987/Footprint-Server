package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.GoalDay;
import com.umc.footprint.src.users.model.GetGoalDays;
import com.umc.footprint.src.users.model.GoalDayInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GoalDayRepository extends JpaRepository<GoalDay, Integer> {

    List<GoalDay> findByUserIdx(int userIdx);

    @Query(value = "SELECT * FROM GoalDay " +
            "WHERE userIdx = :userIdx AND YEAR(createAt)= :nowYear AND MONTH(createAt)=:nowMonth", nativeQuery = true)
    Optional<GoalDay> selectGoalDayByQuery(@Param(value = "userIdx") int userIdx,
                                           @Param(value = "nowYear") int nowYear,
                                           @Param(value = "nowMonth") int nowMonth);

    @Query(value = "SELECT EXISTS(SELECT * FROM GoalDay WHERE userIdx = :userIdx AND " +
            "MONTH(createAt) = MONTH(DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 MONTH))) AS success",
    nativeQuery = true)
    int checkUserPrevGoalDayByQuery(@Param(value = "userIdx") int userIdx);

    @Query(value = "SELECT sun, mon, tue, wed, thu, fri, sat FROM GoalDay " +
            "WHERE userIdx = :userIdx AND YEAR(createAt)= :nowYear AND MONTH(createAt)=:nowMonth", nativeQuery = true)
    Optional<GoalDayInterface> selectOnlyGoalDayByQuery(@Param(value = "userIdx") int userIdx,
                                                    @Param(value = "nowYear") int nowYear,
                                                    @Param(value = "nowMonth") int nowMonth);

}
