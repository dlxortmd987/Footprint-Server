package com.umc.footprint.src.course.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseStatus {
    ACTIVE("활성"),
    INACTIVE("비활성"),
    REPORT("신고"),
    ;

    private final String description;
}
