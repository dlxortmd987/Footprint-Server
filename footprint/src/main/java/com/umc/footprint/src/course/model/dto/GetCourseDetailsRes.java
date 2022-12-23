package com.umc.footprint.src.course.model.dto;

import com.umc.footprint.src.common.model.vo.HashtagInfo;
import com.umc.footprint.src.course.model.dto.projection.HashTagProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetCourseDetailsRes {

    @ApiModelProperty(value = "수정하는 코스 인덱스", name = "courseIdx", dataType = "int")
    private Integer courseIdx;

    @ApiModelProperty(value = "코스 주소", name = "address", dataType = "String", example = "서울 성북구")
    private String address;

    @ApiModelProperty(value = "코스 상세설명", name = "description", dataType = "String", example = "테스트 코스 설명.\\n")
    private String description;

    @ApiModelProperty(value = "원본 산책 인덱스", name = "walkIdx", dataType = "int")
    private Integer walkIdx;

    @ApiModelProperty(value = "코스 소요 시간", name = "courseTime", dataType = "int", example = "20")
    private Integer courseTime;

    @ApiModelProperty(value = "산책 거리", name = "distance", dataType = "double", example = "10.5")
    private Double distance;

    @ApiModelProperty(value = "코스 대표 사진", name = "courseImg", dataType = "String")
    private String courseImg;

    @ApiModelProperty(value = "산책의 모든 해시태그 리스트", name = "allHashtags", dataType = "List<Object>")
    private List<HashtagInfo> allHashtags = new ArrayList<>();

    @ApiModelProperty(value = "코스의 선택된 해시태그 리스트", name = "selectedHashtags", dataType = "List<Object>")
    private List<HashtagInfo> selectedHashtags = new ArrayList<>();

    @Builder
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
