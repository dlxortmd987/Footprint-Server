package com.umc.footprint.src.course.repository;

import com.umc.footprint.src.course.model.entity.Course;
import com.umc.footprint.src.course.model.vo.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Boolean existsByCourseNameAndStatus(String courseName, CourseStatus status);

    Course findByCourseNameAndStatus(String courseName, CourseStatus status);

    List<Course> findAllByStatus(CourseStatus status);

    Optional<Course> findByCourseIdx(int courseIdx);

    @Query(value = "SELECT * FROM Course WHERE courseIdx IN (:courseIdxes)", nativeQuery = true)
    List<Course> getAllByCourseIdx(@Param("courseIdxes") List<Integer> courseIdxes);

    List<Course> getAllByUserIdx(int userIdx);

}
