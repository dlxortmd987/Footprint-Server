package com.umc.footprint.src.course.model.dto;

import com.umc.footprint.src.common.model.vo.HashtagInfo;
import com.umc.footprint.src.course.model.dto.projection.HashTagProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetCourseDetailsRes {
    private Integer courseIdx;
    private String address;
    private String description;
    private Integer walkIdx;
    private Integer courseTime;
    private Double distance;
    private String courseImg;
    private List<ArrayList<Double>> coordinates;
    private List<HashtagInfo> allHashtags = new ArrayList<>();
    private List<HashtagInfo> selectedHashtags = new ArrayList<>();

    @Builder
    public GetCourseDetailsRes(Integer courseIdx, String address, String description, Integer walkIdx, Integer courseTime, Double distance, String courseImg, List<ArrayList<Double>> coordinates, List<HashtagInfo> allHashtags, List<HashtagInfo> selectedHashtags) {
        this.courseIdx = courseIdx;
        this.address = address;
        this.description = description;
        this.walkIdx = walkIdx;
        this.courseTime = courseTime;
        this.distance = distance;
        this.courseImg = courseImg;
        this.coordinates = coordinates;
        this.allHashtags = allHashtags;
        this.selectedHashtags = selectedHashtags;
    }

    public GetCourseDetailsRes(Integer courseIdx, String address, String description, Integer walkIdx, Integer courseTime, Double distance, String courseImg, List<HashTagProjection> courseAllTags, List<HashTagProjection> courseSelectedCourseTags) {
        this.courseIdx = courseIdx;
        this.address = address;
        this.description = description;
        this.walkIdx = walkIdx;
        this.courseTime = courseTime;
        this.distance = distance;
        this.courseImg = courseImg;
        courseAllTags.forEach(courseAllTag -> allHashtags.add(
                HashtagInfo.builder()
                        .hashtagIdx(courseAllTag.getHashtagIdx())
                        .hashtag(courseAllTag.getHashtag())
                        .build()
        ));

        courseSelectedCourseTags.forEach(selectedHashtag -> selectedHashtags.add(
                HashtagInfo.builder()
                        .hashtagIdx(selectedHashtag.getHashtagIdx())
                        .hashtag(selectedHashtag.getHashtag())
                        .build()
        ));
    }
}
