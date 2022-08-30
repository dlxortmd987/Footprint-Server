package com.umc.footprint.src.walks.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostCourseDetailsReq {
    private String courseName;
    private String courseImg;
    private List<List<Double>> coordinates;
    private List<HashtagVO> hashtags;
    private String address;
    private Double length;
    private Integer courseTime;
    private Integer walkIdx;
    private String description;

    @Builder
    public PostCourseDetailsReq(String courseName, String courseImg, List<List<Double>> coordinates, List<HashtagVO> hashtags, String address, Double length, Integer courseTime, Integer walkIdx, String description) {
        this.courseName = courseName;
        this.courseImg = courseImg;
        this.coordinates = coordinates;
        this.hashtags = hashtags;
        this.address = address;
        this.length = length;
        this.courseTime = courseTime;
        this.walkIdx = walkIdx;
        this.description = description;
    }
}
