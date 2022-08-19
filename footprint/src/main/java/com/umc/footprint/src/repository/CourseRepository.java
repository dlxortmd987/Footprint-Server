package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
