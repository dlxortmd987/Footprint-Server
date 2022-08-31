package com.umc.footprint.src.course.repository;

import com.umc.footprint.src.course.model.entity.Course;
import com.umc.footprint.src.course.model.entity.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseTagRepository extends JpaRepository<CourseTag, Integer> {
    List<CourseTag> findAllByCourseAndStatus(Course course, String status);

    List<CourseTag> findAllByCourse(Course course);
}
