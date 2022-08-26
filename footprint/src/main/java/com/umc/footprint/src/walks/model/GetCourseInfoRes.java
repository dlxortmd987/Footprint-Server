package com.umc.footprint.src.walks.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class GetCourseInfoRes {
    private List<ArrayList<Double>> coordinate;
    private String courseDisc;

    @Builder
    public GetCourseInfoRes(List<ArrayList<Double>> coordinate, String courseDisc) {
        this.coordinate = coordinate;
        this.courseDisc = courseDisc;
    }
}
