package com.umc.footprint.src.course.model.dto;

import com.umc.footprint.src.course.model.vo.CourseInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class GetCourseListRes {
    private final List<CourseInfo> getCourseLists;
}
