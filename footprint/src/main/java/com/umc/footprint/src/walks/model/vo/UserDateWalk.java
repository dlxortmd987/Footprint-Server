package com.umc.footprint.src.walks.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/*
 * userIdx, date에 해당하는 산책 정보
 * */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDateWalk {

    @ApiModelProperty(value = "산책 인덱스", example = "3")
    private int walkIdx;

    @ApiModelProperty(value = "산책 시작 시간", example = "14:00")
    private String startTime;

    @ApiModelProperty(value = "산책 마친 시간", example = "14:20")
    private String endTime;

    @ApiModelProperty(value = "산책 동선 이미지", example = "https://t1.daumcdn.net/cfile/blog/9990B6445E22F3BE2C")
    private String pathImageUrl;

    @Builder
    public UserDateWalk(int walkIdx, String startTime, String endTime, String pathImageUrl){
        this.walkIdx = walkIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pathImageUrl = pathImageUrl;
    }

    public void setDecryptedPathImageUrl(String decrypt) {
        pathImageUrl = decrypt;
    }

    public void setWalkIdx(int getWalkIdx) {
        walkIdx = getWalkIdx;

    }
}
