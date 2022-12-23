package com.umc.footprint.src.common.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagInfo {

    @ApiModelProperty(value = "해시태그 인덱스", name = "hashtagIdx", dataType = "int")
    private Integer hashtagIdx;

    @ApiModelProperty(value = "해시태그 내용", name = "hashtag", dataType = "String", example = "#테스트")
    private String hashtag;

    @Builder
    public HashtagInfo(Integer hashtagIdx, String hashtag) {
        this.hashtagIdx = hashtagIdx;
        this.hashtag = hashtag;
    }
}
