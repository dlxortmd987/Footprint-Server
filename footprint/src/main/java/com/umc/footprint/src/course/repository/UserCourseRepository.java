package com.umc.footprint.src.course.repository;

import com.umc.footprint.src.course.model.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    Optional<UserCourse> findByCourseIdxAndUserIdx(Integer courseIdx, Integer userIdx);

    List<UserCourse> findByUserIdx(int userIdx);

    List<UserCourse> findByCourseIdx(int courseIdx);
}
