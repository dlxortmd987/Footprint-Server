package com.umc.footprint.src.course.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetCourseListReq {
    private double north;
    private double south;
    private double west;
    private double east;

    @Builder
    public GetCourseListReq(double north, double south, double west, double east) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }
}
