package com.umc.footprint.src.walks.model;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetWalkInfo {
    private final int walkIdx;
    private final GetWalkTime getWalkTime;
    private final int calorie;
    private final double distance;
    private final int footCount;
    private final String pathImageUrl;
    private final List<ArrayList<Double>> coordinate;
}
