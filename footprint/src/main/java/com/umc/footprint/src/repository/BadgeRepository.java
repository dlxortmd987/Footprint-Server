package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    Badge getByBadgeIdx(int badgeIdx);

    @Query(value = "SELECT * FROM Badge WHERE badgeIdx IN (:badgeIdx)",
            nativeQuery = true)
    List<Badge> findByBadgeIdx(@Param("badgeIdx") List<Integer> badgeIdx);
    boolean existsByBadgeIdx(int badgeIdx);
}