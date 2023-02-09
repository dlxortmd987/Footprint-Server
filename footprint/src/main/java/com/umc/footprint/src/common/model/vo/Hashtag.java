package com.umc.footprint.src.common.model.vo;

import lombok.*;

/*
 * walkIdx 와 hashtag 정보
 * */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {
    private int walkIdx;
    private String hashtag;

    @Builder
    public Hashtag(int walkIdx, String hashtag){
        this.walkIdx = walkIdx;
        this.hashtag = hashtag;
    }
}
