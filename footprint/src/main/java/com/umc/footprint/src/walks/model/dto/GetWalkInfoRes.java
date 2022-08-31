package com.umc.footprint.src.walks.model.dto;

import com.umc.footprint.src.walks.model.vo.WalkTime;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetWalkInfoRes {
    private final int walkIdx;
    private final WalkTime walkTime;
    private final int calorie;
    private final double distance;
    private final int footCount;
    private final List<List<Double>> footCoordinates;
    private final String pathImageUrl;
    private final List<ArrayList<Double>> coordinate;
}
