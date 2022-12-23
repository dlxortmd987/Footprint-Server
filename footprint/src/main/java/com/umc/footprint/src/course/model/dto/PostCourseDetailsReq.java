package com.umc.footprint.src.course.model.dto;

import com.umc.footprint.src.common.model.vo.HashtagInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostCourseDetailsReq {


    @ApiModelProperty(value = "코스 이름", name = "courseName", dataType = "String", required = true, example = "테스트 코스")
    private String courseName;

    @ApiModelProperty(value = "코스 이미지", name = "courseImg", dataType = "String", required = true)
    private String courseImg;

    @ApiModelProperty(value = "사용자가 설정한 코스 좌표", name = "coordinates", dataType = "List<List<Double>>", required = true, example = "[[1.1, 2.2, 1.2, 2.2]]")
    private List<List<Double>> coordinates;

    @ApiModelProperty(value = "사용자가 선택한 해시태그", name = "hashtags", dataType = "List<Object>")
    private List<HashtagInfo> hashtags;

    @ApiModelProperty(value = "사용자 주소", name = "address", dataType = "String", example = "서울 상도동")
    private String address;

    @ApiModelProperty(value = "코스 길이", name = "length", dataType = "double", required = true, example = "1.123")
    private Double length;

    @ApiModelProperty(value = "코스 소요 시간", name = "courseTime", dataType = "int", required = true)
    private Integer courseTime;

    @ApiModelProperty(value = "코스 원본 산책 인덱스", name = "walkIdx", dataType = "int", required = true)
    private Integer walkIdx;

    @ApiModelProperty(value = "코스 설명", name = "description", dataType = "String", example = "테스트 설명")
    private String description;

    @Builder
    public PostCourseDetailsReq(String courseName, String courseImg, List<List<Double>> coordinates, List<HashtagInfo> hashtags, String address, Double length, Integer courseTime, Integer walkIdx, String description) {
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
