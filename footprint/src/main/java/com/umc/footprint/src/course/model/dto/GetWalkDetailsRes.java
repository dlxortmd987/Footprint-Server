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
public class GetWalkDetailsRes {
    private final Integer walkIdx;
    private final Integer walkTime;
    private final Double distance;
    private final List<ArrayList<Double>> coordinates;
    private final List<HashtagInfo> hashtags;
    private final List<String> photos;
}
