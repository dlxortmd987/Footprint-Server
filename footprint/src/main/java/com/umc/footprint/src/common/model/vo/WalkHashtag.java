package com.umc.footprint.src.common.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WalkHashtag {
    private Integer walkIdx;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String pathImageUrl;
    private String hashtag;

    @Builder
    public WalkHashtag(Integer walkIdx, LocalDateTime startAt, LocalDateTime endAt, String pathImageUrl, String hashtag) {
        this.walkIdx = walkIdx;
        this.startAt = startAt;
        this.endAt = endAt;
        this.pathImageUrl = pathImageUrl;
        this.hashtag = hashtag;
    }
}
