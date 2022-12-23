package com.umc.footprint.src.course.model.dto;

import com.umc.footprint.src.common.model.vo.HashtagInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class GetWalkDetailsRes {

    @ApiModelProperty(value = "산책 인덱스", name = "walkIdx", dataType = "int")
    private final Integer walkIdx;

    @ApiModelProperty(value = "산책시간", name = "walkTime", dataType = "int", example = "20")
    private final Integer walkTime;

    @ApiModelProperty(value = "산책 거리", name = "distance", dataType = "double", example = "1.234")
    private final Double distance;

    @ApiModelProperty(value = "산책 좌표", name = "coordinates", dataType = "List<List<Double>>", example = "[[1.1, 2.2, 1.2, 2.2], [1.1, 2.2, 1.2, 2.2]]")
    private final List<ArrayList<Double>> coordinates;

    @ApiModelProperty(value = "산책의 해시태그 리스트", name = "hashtags", dataType = "List<Object>")
    private final List<HashtagInfo> hashtags;

    @ApiModelProperty(value = "산책의 사진 리스트", name = "photos", dataType = "List<String>", example = "[\"url1\", \"url2\"]")
    private final List<String> photos;
}
