package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Walk;
import com.umc.footprint.src.walks.model.ObtainedBadgeInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface WalkRepository extends JpaRepository<Walk, Integer> {
    @Query(value = "SELECT \n" +
            "       CASE\n" +
            "            WHEN (sum(Walk.distance) between 10 and 30) then 2\n" +
            "            when (sum(Walk.distance) between 30 and 50) then 3\n" +
            "            WHEN (sum(Walk.distance) between 50 and 100) then 4\n" +
            "            WHEN (sum(Walk.distance) > 100) then 5\n" +
            "        else 0\n" +
            "        end as distanceBadgeIdx,\n" +
            "       CASE\n" +
            "            when (count(Walk.walkIdx) = 1) then 1" +
            "            when (count(Walk.walkIdx) between 10 and 19) then 6\n" +
            "            when (count(Walk.walkIdx) between 20 and 29) then 7\n" +
            "            when (count(Walk.walkIdx) >= 30) then 8\n" +
            "        else 0\n" +
            "        end as recordBadgeIdx\n" +
            "From Walk\n" +
            "Where userIdx = ?1\n and status = 'ACTIVE'" +
            "group by Walk.userIdx", nativeQuery = true)
    ObtainedBadgeInterface getAcquiredBadgeIdxList(int userIdx);

    boolean existsByUserIdx(Integer userIdx);

    List<Walk> findAllByStatusAndUserIdx(String status,int userIdx);

    Optional<Walk> findByWalkIdx(Integer walkIdx);

    List<Walk> findAllByUserIdx(int userIdx);
}