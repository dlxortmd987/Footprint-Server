package com.umc.footprint.src.walks.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class GetCourseListRes {
    private double startLat;
    private double startLong;
    private String courseName;
    private double courseDist;
    private int courseTime;
    private int courseMark;
    private int courseLike;
    private List<String> courseTags;
    private String courseImg;
    private boolean userCourseLike;

    @Builder
    public GetCourseListRes(double startLat, double startLong, String courseName, double courseDist, int courseTime, int courseMark, int courseLike, List<String> courseTags, String courseImg, boolean userCourseLike) {
        this.startLat = startLat;
        this.startLong = startLong;
        this.courseName = courseName;
        this.courseDist = courseDist;
        this.courseTime = courseTime;
        this.courseMark = courseMark;
        this.courseLike = courseLike;
        this.courseTags = courseTags;
        this.courseImg = courseImg;
        this.userCourseLike = userCourseLike;
    }
}
