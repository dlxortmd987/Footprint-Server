package com.umc.footprint.src.course.model.dto;

import com.umc.footprint.src.common.model.vo.HashtagInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetCourseDetailsRes {
    private final Integer courseIdx;
    private final String address;
    private final String description;
    private final Integer walkIdx;
    private final Integer courseTime;
    private final Double distance;
    private final String courseImg;
    private final List<ArrayList<Double>> coordinates;
    private final List<HashtagInfo> allHashtags;
    private final List<HashtagInfo> selectedHashtags;
}
