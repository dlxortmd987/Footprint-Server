package com.umc.footprint.src.common.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagInfo {
    private Integer hashtagIdx;
    private String hashtag;

    @Builder
    public HashtagInfo(Integer hashtagIdx, String hashtag) {
        this.hashtagIdx = hashtagIdx;
        this.hashtag = hashtag;
    }
}
