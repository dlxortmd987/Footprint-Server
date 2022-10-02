package com.umc.footprint.src.course.repository;

import com.umc.footprint.src.course.model.dto.projection.CourseHashTagProjection;
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

    List<Course> getAllByUserIdxAndStatus(int userIdx, CourseStatus status);

    @Query(
            value = "select distinct c.courseIdx as courseIdx, c.address as address, c.description as description, c.walkIdx as walkIdx, w.startAt as startAt, w.endAt as endAt, w.distance as distance, c.courseImg as courseImg " +
                    "from Course c " +
                    "join Walk w on c.walkIdx = w.walkIdx " +
                    "where BINARY c.courseName = ? and c.status = 'ACTIVE' and w.status = 'ACTIVE'"
            ,nativeQuery = true
    )
    CourseHashTagProjection findCourseDetails(@Param("courseName") String courseName);



}
