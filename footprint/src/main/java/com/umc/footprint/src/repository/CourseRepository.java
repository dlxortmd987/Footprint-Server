package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findAllByStatus(String status);

    Optional<Course> findByCourseIdx(int courseIdx);

}
