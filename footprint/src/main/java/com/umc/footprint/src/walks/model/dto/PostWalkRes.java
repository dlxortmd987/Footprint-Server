package com.umc.footprint.src.walks.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostWalkRes {
    // 뱃지 이름, 뱃지 url
    @ApiModelProperty(value = "뱃지 인덱스", example = "2")
    private int badgeIdx;

    @ApiModelProperty(value = "뱃지 이름", example = "누적 10km")
    private String badgeName;

    @ApiModelProperty(value = "뱃지 사진 Url")
    private String badgeUrl;

    @Builder
    public PostWalkRes(int badgeIdx, String badgeName, String badgeUrl) {
        this.badgeIdx = badgeIdx;
        this.badgeName = badgeName;
        this.badgeUrl = badgeUrl;
    }
}
