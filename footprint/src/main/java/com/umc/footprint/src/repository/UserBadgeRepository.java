package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {

    Optional<List<UserBadge>> findAllByUserIdxAndStatus(@Param(value = "userIdx") Integer userIdx, @Param(value = "status") String status);

    List<UserBadge> findAllByUserIdx(int userIdx);

    @Query(value = "SELECT EXISTS (" +
            "SELECT badgeIdx FROM UserBadge WHERE userIdx=:userIdx AND badgeIdx=:badgeIdx AND status='ACTIVE' limit 1) AS success",
    nativeQuery = true)
    int checkUserHasBadge(@Param(value = "userIdx") int userIdx,
                              @Param(value = "badgeIdx") int badgeIdx);

}
