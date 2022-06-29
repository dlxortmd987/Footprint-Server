package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {
    Optional<List<UserBadge>> findAllByUserIdxAndStatus(@Param(value = "userIdx") Integer userIdx, @Param(value = "status") String status);

    List<UserBadge> findAllByUserIdx(int userIdx);
}
