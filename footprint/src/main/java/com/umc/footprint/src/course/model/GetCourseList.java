package com.umc.footprint.src.course.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class GetCourseList {
    private final List<GetCourseListRes> getCourseLists;
}
