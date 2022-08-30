package com.umc.footprint.src.walks.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagVO {
    private Integer hashtagIdx;
    private String hashtag;

    @Builder
    public HashtagVO(Integer hashtagIdx, String hashtag) {
        this.hashtagIdx = hashtagIdx;
        this.hashtag = hashtag;
    }
}
