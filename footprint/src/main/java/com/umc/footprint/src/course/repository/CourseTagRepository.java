package com.umc.footprint.src.course.repository;

import com.umc.footprint.src.course.model.dto.projection.HashTagProjection;
import com.umc.footprint.src.course.model.entity.Course;
import com.umc.footprint.src.course.model.entity.CourseTag;
import com.umc.footprint.src.course.model.vo.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseTagRepository extends JpaRepository<CourseTag, Integer> {
    List<CourseTag> findAllByCourseAndStatus(Course course, CourseStatus status);

    @Query(
            value = "select h.hashtagIdx as hashtagIdx, h.hashtag as hashtag " +
                    "from CourseTag ct " +
                    "join Hashtag h on h = ct.hashtag " +
                    "where ct.course.courseIdx = :courseIdx and ct.status = 'ACTIVE'"
    )
    List<HashTagProjection> findCourseSelectedTags(@Param("courseIdx") Integer courseIdx);
}
